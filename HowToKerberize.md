![Bee waggle-dancing on a hive.](logo.png "Federating Hive Meta Stores.")

# Additional instruction to use Waggle Dance in kerberized environment
 
 
### Prerequisites

* Kerberized claster: 
    active KDC, 
    some additional properties in configuration of hadoop services



### Required Hadoop configuration

#### *HDFS (in core-site.yaml)*

All of clusters you want to use with WD should be kerberized with one KDC and use one IPA service. 
Also make sure you've set up a federation and nameservices resolution between clusters.
Otherwise, non of users won't have permissions to different hdfs'.

Some of required properties: 
* hadoop.security.authentication=KERBEROS
* dfs.nameservices=nameservice1,nameservise2
* dfs.namenode.rpc-address.nameservice1.namenode=<namenode_address>:<namenode_port>
* and so on for each cluster (more detailed in community guidelines)

#### *YARN (in yarn-site.yaml)*

YARN may need some additional settings for DelegationToken forwarding.
Otherwise, you won't be able to start mapreduce jobs on an external hdfs.

The property:
* mapreduce.job.hdfs-servers=hdfs://nameservice1,hdfs://nameservice2

#### *HIVE*

Make sure that every Hive service has overridden value of uri to metastore replaced with uri to WD. 
You may use this property:
* hive.metastore.uris=thrift://<wd_address>:<wd_port>

#### SPARK
If you want to use Spark sql  with WD, you also should override uri to metastore and resolve nameservices:

* spark.yarn.access.hadoopFileSystems=hdfs://nameservice1:8020/,hdfs://nameservice2:8020/ 
* spark.hive.metastore.uris=thrift://<wd_address>:<wd_port>


### WD Configuration

To work within kerberized environment WD should have keytab file: its own (with permissions) or hive.keytab.  
WD will act as hive or its own user according to this keytab file.
If impersonation mode is active (*hive.server2.enable.impersonation* and *hive.server2.enable.doAs* are "true"), WD will 
forward credentials of a real user for every request made.

Waggle Dance use it's own config file (waffle-dance-server.yml) to gain kerberos configuration. 
So you have to add all hive's and hadoop's properties to the block "configurationProperties". 
```
configurationProperties:
    hadoop.security.authentication: KERBEROS
    hive.metastore.authentication: KERBEROS
    hive.metastore.kerberos.principal: hive/_HOST@DEV.DF.SBRF.RU
    hive.metastore.kerberos.keytab.file: /etc/hive.keytab
    hive.metastore.sasl.enabled: true
```

### Running 

The Waggle Dance should be starting as privileged user with a fresh keytab.

If Waggle Dance throws a GSS exception, you have problem with the keytab file.
Try to perform `kdestroy` and `kinit` operations and check for a keytab file ownership.


