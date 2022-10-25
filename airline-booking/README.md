### Funcitonal Requirements
1. Booking for a particular airline
2. Search for a airline
3. Booking a flight
4. Payment services 3rd party

Optional
5. Notification
6. Analytics

### NonFuncitonal Requirements
1. Consistency for booking, payment
2. High availability for other services
3. Low latency
4. Users is multiple regions

### Estimates
1. Total Users: 10K + (10% growth)
2. DAU: 
3. Number of search requests - 100k/day
4. Number of transactions - 1k/day
5. 100 flights per day. 1 flight -> 250 seats

#### Assumptions
##### Capacity Estimations
User - 100bytes = 100bytes * 10K = 1GB + (100mb every year * 5years)

