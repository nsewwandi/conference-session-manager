# AWS Deployment and Performance Optimization

## 1. AWS Design Considerations

### **1. Compute Layer**

**EC2 Server with Auto Scaling Group**

- Deploy the application on an **EC2 instance** with an **Auto Scaling Group (ASG)** to ensure redundancy.
- Use an **Elastic Load Balancer (ALB)** to distribute traffic efficiently.
- Deploy EC2 instances **inside a private subnet** for better security.

### **2. Database Layer**

**Amazon RDS for MySQL**

- Deploy a managed **Amazon RDS MySQL** instance for reliability and automatic backups.
- Enable **Multi-AZ** deployment for high availability.
- Utilize **Read Replicas** for better scalability.

### **3. Storage Layer**

- **Amazon S3**: Store logs and database backups securely.

### **4. Security Measures**

- **IAM Roles & Policies**: Secure access to AWS resources.
- **API Gateway**: Use AWS API Gateway if exposing APIs externally for security and rate limiting.
- **AWS WAF**: Protect against SQL injection, XSS, and DDoS attacks.
- **VPC with Private Subnets**:
  - Deploy RDS and EC2 instances **in private subnets**.
  - Use a **NAT Gateway** for outgoing internet access.
- **Secrets Manager / Parameter Store**: Store database credentials securely.

### **5. Scaling Considerations**

- **ECS Fargate Auto Scaling**: Adjust container instances based on CPU/Memory utilization.
- **RDS Read Replicas**: Scale read-heavy workloads efficiently.
- **Application Load Balancer (ALB)**: Distributes traffic across multiple instances/containers.
- **Amazon CloudWatch**: Monitor application metrics and set up alerts.

---

## 2. Debugging & Performance Optimization

- Use **Postman, cURL, or JMeter** to test `/sessions` endpoint and confirm slow response times.
- Check **response time metrics** in Amazon CloudWatch, Datadog, or a logging tool.
- Optimize database queries:
  - Fetch only necessary fields instead of `SELECT *`:
    ```sql
    SELECT id, title, speaker FROM sessions;
    ```
  - Implement **pagination** using `LIMIT` and `OFFSET`:
    ```sql
    SELECT * FROM sessions ORDER BY created_at DESC LIMIT 50 OFFSET 0;
    ```
  - Cache frequent queries using **Redis** or **Amazon ElastiCache**.
- Use **Batch Processing** for large datasets instead of fetching everything at once.
- Profile the API using **JProfiler** or **Dropwizard Metrics** to find bottlenecks.
- Implement **caching** using **Redis** or **Dropwizard Caching**.

---

## 3. Security Measures for DDoS Protection

- Analyze **API Gateway or Load Balancer logs** for unusual traffic spikes.
- Implement **Rate Limiting** using Dropwizard’s RateLimiter or Redis.
- Use **IP-based blocking** for malicious requests.
- Configure **AWS WAF** to block abusive traffic patterns.
- Utilize **AWS Shield** to protect against volumetric DDoS attacks.
- Reject **malformed or oversized requests**.
- Validate **headers, query parameters, and JSON payloads** before processing.

---

## 4. Code Review - Identified Issues & Fixes

### **Issue 1: Unhandled SQLException**

**Problem:**

- The method does **not handle SQLExceptions**, leading to application crashes.

**Solution:**

- Use `try-catch-finally` to properly handle exceptions.
- Log or rethrow meaningful error messages.

### **Issue 2: Hardcoded Database Credentials**

**Problem:**

- Storing database credentials **directly in code** is a major security risk.

**Solution:**

- Use **environment variables** or a **configuration file** for credentials.

### **Issue 3: No Resource Cleanup (Connection Leak Risk)**

**Problem:**

- **Connection, Statement, and ResultSet** are not closed, leading to potential **memory leaks**.

**Solution:**

- Use **try-with-resources  to ensure automatic cleanup of database connections or or close resources manually in finally block.**


