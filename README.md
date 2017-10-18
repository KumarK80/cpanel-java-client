# Cpanel Java Client (Under Development)
A java client library for cpanel api.
## Currently Implemented Features:
1. Basic Authentication Only.
2. Subdomain
  a. Get all subdomain.
  b. Destroy subdomain.
  c. Create subdomain.

## Usage:
Step 1: Create Cpanel Client Context.
`CpanelClient.Context cpanelClientContext = new CpanelClient.Context(<host-ip-address>, <cpanel-port>, <username>, <password>);`

Step 2: Create Client Factory.
`CpanelClientFactory cpanelClientFactory = CpanelClientFactory.getInstance(cpanelClientContext);`

Step 3: Get Client.
`CpanelSubDomainClient subDomainClient = cpanelClientFactory.getSubDomainClient();`

Step 4: Use Client.
`List<CpanelSubDomain> sudDomains = subDomainClient.getAllSubDomain();`