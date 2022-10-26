package code.shubham.authentication;

import code.shubham.authentication.constants.AuthenticationConstants;
import code.shubham.authentication.dao.entities.KeyStore;
import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.authentication.dao.repositories.AccountRepository;
import code.shubham.authentication.dao.repositories.KeyRepository;
import code.shubham.commons.annotations.SpringBootJpaApplication;
import code.shubham.commons.contexts.UserContextHolder;
import code.shubham.encryption.keys.asymmetric.RSAUtil;
import code.shubham.encryption.keys.symmetric.AESUtil;
import code.shubham.encryption.keys.symmetric.AES_KEY_SIZE;
import code.shubham.keystore.KeyStoreType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import javax.crypto.SecretKey;
import java.security.KeyPair;

import static code.shubham.keystore.KeyStoreUtils.createKeyStoreAndGetBytes;

@SpringBootJpaApplication
public class AuthenticationApplication implements CommandLineRunner {

	@Autowired
	KeyRepository keyRepository;

	@Autowired
	private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserAccount account = this.createSystemAccount();
		UserContextHolder.setUserId(account.getId());
		this.create_DB_PASSWORD_ENCRYPTION_KEY();
		this.create_DB_ACCESS_TOKEN_ENCRYPTION_KEY();
	}

	private UserAccount createSystemAccount() {
		var defaultSystemAccount = this.accountRepository.findByUsername("SYSTEM_DEFAULT");
		if (defaultSystemAccount.isPresent())
			return defaultSystemAccount.get();
		UserContextHolder.setUserId(0);
		UserAccount account = this.accountRepository.save(
				UserAccount.builder().username("SYSTEM_DEFAULT").isEnabled(true).password("").build());
		System.out.println("default system account created");
		return account;
	}

	public void create_DB_PASSWORD_ENCRYPTION_KEY() throws Exception {
		KeyStore keyStore = this.keyRepository.findByPurpose(AuthenticationConstants.DB_PASSWORD_ENCRYPTION_KEY);
		if (keyStore != null)
			return;

		String keyStorePath = "keystores/keystore_DB_PASSWORD_ENCRYPTION_KEY.p12";
		String password = "#@$%ui324";
		String alias = AuthenticationConstants.DB_PASSWORD_ENCRYPTION_KEY;

		KeyPair keyPair = RSAUtil.generate();
		byte[] keyStoreBytes = createKeyStoreAndGetBytes(
				keyStorePath, KeyStoreType.pkcs12, password, alias, keyPair.getPrivate(), keyPair.getPublic());
		keyStore = KeyStore.builder().
				key(keyStoreBytes).
				alias(alias).
				password(password).
				purpose(AuthenticationConstants.DB_PASSWORD_ENCRYPTION_KEY).
				build();
		keyStore = this.keyRepository.save(keyStore);

		byte[] data = "ljdlkasjdlasjdlsajdljsaldjaldjalsjd".getBytes();
		byte[] encrypted = RSAUtil.encrypt(keyPair.getPublic(), data);
		byte[] decrypted = RSAUtil.decrypt(keyStore.getPrivateKey(), encrypted);

		if (new String(data).equals(new String(decrypted)))
			System.out.println(true);


		encrypted = RSAUtil.encrypt(keyStore.getPublicKey(), data);
		decrypted = RSAUtil.decrypt(keyStore.getPrivateKey(), encrypted);
		if (new String(data).equals(new String(decrypted)))
			System.out.println(true);
	}

	public void create_DB_ACCESS_TOKEN_ENCRYPTION_KEY() throws Exception {
		KeyStore keyStore = this.keyRepository.findByPurpose(AuthenticationConstants.DB_ACCESS_TOKEN_ENCRYPTION_KEY);
		if (keyStore != null)
			return;

		String keyStorePath = "keystores/keystore_DB_ACCESS_TOKEN_ENCRYPTION_KEY.p12";
		String password = "#@$%ui324";
		String alias = AuthenticationConstants.DB_ACCESS_TOKEN_ENCRYPTION_KEY;
        SecretKey secretKey = AESUtil.generateKey(AES_KEY_SIZE._256);

		byte[] keyStoreBytes = createKeyStoreAndGetBytes(
				keyStorePath, KeyStoreType.pkcs12, password, alias, secretKey);
		keyStore = KeyStore.builder().
				key(keyStoreBytes).
				alias(alias).
				password(password).
				purpose(AuthenticationConstants.DB_ACCESS_TOKEN_ENCRYPTION_KEY).
				build();
		keyStore = this.keyRepository.save(keyStore);

		byte[] data = "ljdlkasjdlasjdlsajdljsaldjaldjalsjd".getBytes();
		byte[] encrypted = AESUtil.encrypt(secretKey, data);
		byte[] decrypted = AESUtil.decrypt(keyStore.getSecretKey(), encrypted);

		if (new String(data).equals(new String(decrypted)))
			System.out.println(true);
	}
}
