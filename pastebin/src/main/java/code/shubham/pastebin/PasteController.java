package code.shubham.pastebin;

import code.shubham.models.paste.CreatePasteRequest;
import code.shubham.models.paste.CreatePasteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PasteController {
    private final PasteService pasteService;

    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    public CreatePasteResponse create(
            @RequestHeader("userId") Integer userId,
            @RequestBody CreatePasteRequest request) {
        return this.pasteService.create(request, userId);
    }

    @GetMapping

}
