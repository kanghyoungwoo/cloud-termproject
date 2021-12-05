package awsTest;

import java.util.Scanner;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.Region;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.DescribeRegionsResult;

import com.amazonaws.services.ec2.model.DryRunResult;
import com.amazonaws.services.ec2.model.DryRunSupportedRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;

import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
//import com.amazonaws.services.ec2.model.CreateTagsRequest;
//import com.amazonaws.services.ec2.model.CreateTagsResult;
//import com.amazonaws.services.ec2.model.Tag;

import com.amazonaws.services.ec2.model.RebootInstancesRequest;
import com.amazonaws.services.ec2.model.RebootInstancesResult;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;

import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.DescribeImagesRequest;
import com.amazonaws.services.ec2.model.DescribeImagesResult;

import com.amazonaws.services.ec2.model.MonitorInstancesRequest;
import com.amazonaws.services.ec2.model.UnmonitorInstancesRequest;

import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.KeyPairInfo;

import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;

import com.amazonaws.services.ec2.model.DeleteKeyPairRequest;
import com.amazonaws.services.ec2.model.DeleteKeyPairResult;

public class awsTest {
	
	 static AmazonEC2 ec2;
	
	 private static void init() throws Exception {
		 /* * The ProfileCredentialsProvider will return your [default] * credential profile by reading from the credentials file located at * (~/.aws/credentials). */
		 ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		 try { 
			 credentialsProvider.getCredentials();
		 } catch (Exception e) { 
			 throw new AmazonClientException(
					 "Cannot load the credentials from the credential profiles file. " + 
			 "Please make sure that your credentials file is at the correct " + 
							 "location (~/.aws/credentials), and is in valid format.",
							 e);
		 } ec2 = AmazonEC2ClientBuilder.standard()
				 .withCredentials(credentialsProvider) 
				 .withRegion("us-east-2") /* check the region at AWS console */ 
				 .build();
	}
	 
	 
	public static void main(String args[]) throws Exception {
		
		 init(); 
		 
		 Scanner menu = new Scanner(System.in); 
		 Scanner id_string = new Scanner(System.in); 
		 int number = 0;
		 
		 while (true) {
			 System.out.println(" ");
			 System.out.println(" ");
			 System.out.println("------------------------------------------------------------"); 
			 System.out.println(" Amazon AWS Control Panel using SDK "); 
			 System.out.println(" "); 
			 System.out.println(" Cloud Computing, Computer Science Department "); 
			 System.out.println(" at Chungbuk National University "); 
			 System.out.println("------------------------------------------------------------"); 
			 System.out.println(" 1. list instance      2. available zones "); 
			 System.out.println(" 3. start instance     4. available regions "); 
			 System.out.println(" 5. stop instance      6. create instance "); 
			 System.out.println(" 7. reboot instance    8. list images ");
			 System.out.println(" 9. monitor instance  10. unmonitor instance ");
			 System.out.println(" 11. list keypair     12. create keypair ");
			 System.out.println(" 13. delete keypair   99. quit ");
			 System.out.println("------------------------------------------------------------");
			 System.out.print("Enter an integer: "); 
			 number = id_string.nextInt();
			 switch (number) { 
			 case 1: 
				 listInstances(); 
				 break; 
			 case 2: 
				 availableZones(); 
				 break; 
			 case 3: 
				 startInstance(); 
				 break; 
			 case 4: 
				 availableRegions(); 
				 break;
			 case 5: 
				 stopInstance(); 
				 break; 
			 case 6:
				 createInstance(); 
				 break; 
			 case 7: 
				 rebootInstance(); 
				 break; 
			  case 8:
				 listImages();
				 break; 
			  case 9:
				  monitorInstance();
				  break;
			  case 10:
				  unmonitorInstance();
				  break;
			  case 11:
				  listKeypair();
				  break;
			  case 12:
				  createKeypair();
				  break;
			  case 13:
				  deleteKeypair();
				  break;
			  case 99: 
				  System.exit(0);
				  break; 
				  
			  default: 
				  break;
				  } 
			 } 
		 }
	
	 /*1. list instance */
	public static void listInstances() 
	{ 
		System.out.println("Listing instances...."); 
		boolean done = false; 
		DescribeInstancesRequest request = new DescribeInstancesRequest();

		while (!done) 
		{ 
			DescribeInstancesResult response = ec2.describeInstances(request); 

			for (Reservation reservation : response.getReservations()) 
			{
				for (Instance instance : reservation.getInstances())
				{ 
					System.out.printf(
							"[id] %s, " + 
					        "[AMI] %s, " + 
							"[type] %s, " + 
					        "[state] %10s, " + 
							"[monitoring state] %s",
							instance.getInstanceId(), instance.getImageId(), instance.getInstanceType(), 
							instance.getState().getName(), instance.getMonitoring().getState()); 
				} 
				    System.out.println(); 
			} request.setNextToken(response.getNextToken()); 
				    if (response.getNextToken() == null)
				    { 
				    	done = true; 
				    }
		} 
	}
	
	 /*2. available zones */ 
	public static void availableZones() 
	{ 
		//final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaulClient();
		DescribeAvailabilityZonesResult zones_response = ec2.describeAvailabilityZones();
		
		System.out.printf("Available Zones....");
		for (AvailabilityZone zone : zones_response.getAvailabilityZones()) 
		{
            System.out.printf("[id] %s" + " with status %s " + "in region %s\n ", 
            		zone.getZoneName(), zone.getState(), zone.getRegionName());
        } 
	} 
	
	 /*3. start instance */ 
	public static void startInstance() 
	{ 
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient(); 

		

        System.out.println("Enter instance ID : ");
        Scanner scan = new Scanner(System.in);
        //String instance_id = scan.nextLine();
        final String instance_id = scan.nextLine();
            
        //here
		 DryRunSupportedRequest<StartInstancesRequest> dry_request = new DryRunSupportedRequest<StartInstancesRequest>() {
			public Request<StartInstancesRequest> getDryRunRequest() {
			StartInstancesRequest request = new StartInstancesRequest()
			    .withInstanceIds(instance_id);

			return request.getDryRunRequest();
			}
		};
		
        DryRunResult dry_response = ec2.dryRun(dry_request);

        if(!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to start instance %s", instance_id);

            throw dry_response.getDryRunResponse();
        }    
        
        //here
	    StartInstancesRequest request = new StartInstancesRequest().withInstanceIds(instance_id);

	    ec2.startInstances(request);
	    System.out.printf("Successfully started instance %s", instance_id);

        	
	    
	}
	
	/*4. available regions */ 
	public static void availableRegions()
	{ 
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient(); 
		DescribeRegionsResult regions_response = ec2.describeRegions(); 
		for (Region region : regions_response.getRegions()) 
		{ 
			System.out.printf("[region] %15s " + "[endpoint] %s\n", region.getRegionName(), region.getEndpoint()); 
		}
	}
	
	
	 /*5. stop instance */
	public static void stopInstance() 
	{ 
		//final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient(); 
        System.out.println("Enter instance iD : ");
        Scanner scan = new Scanner(System.in);
        //String instance_id = scan.nextLine();
        final String instance_id = scan.nextLine();
        

        //here

        DryRunSupportedRequest<StopInstancesRequest> dry_request = new DryRunSupportedRequest<StopInstancesRequest>() {
			public Request<StopInstancesRequest> getDryRunRequest() {
            StopInstancesRequest request = new StopInstancesRequest()
                .withInstanceIds(instance_id);

            return request.getDryRunRequest();
            }
		};

        DryRunResult dry_response = ec2.dryRun(dry_request);

        if(!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to stop instance %s", instance_id);
            throw dry_response.getDryRunResponse();
        }
        

        StopInstancesRequest request = new StopInstancesRequest().withInstanceIds(instance_id); 
        
        ec2.stopInstances(request);
        System.out.printf("Successfully stop instance %s", instance_id);
        
	}
	

	
	 /*6. create instance */ 
	public static void createInstance() 
	{ 
		Scanner scan = new Scanner(System.in); 

		System.out.println("Enter ami id:"); 
		String ami_id = scan.nextLine(); 
		
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
		
		RunInstancesRequest run_request = new RunInstancesRequest()
				.withImageId(ami_id)
				.withInstanceType(InstanceType.T2Micro)
				.withMaxCount(1)
				.withMinCount(1); 
		
		RunInstancesResult run_response = ec2.runInstances(run_request); 
		
		String reservation_id = run_response.getReservation().getInstances().get(0).getInstanceId(); 
		
        //Tag tag = new Tag()
        //        .withKey("Name")
        //        .withValue(name);

        //    CreateTagsRequest tag_request = new CreateTagsRequest()
        //        .withResources(reservation_id)
        //        .withTags(tag);

        //    CreateTagsResult tag_response = ec2.createTags(tag_request);
		
		System.out.println("Successfully started EC2 instance " + reservation_id + " based on AMI " + ami_id); 
	}
	
	//7.rebootInstance
	public static void rebootInstance()
	{
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        System.out.println("Enter instance iD : ");
        Scanner scan = new Scanner(System.in);
        String instance_id = scan.nextLine();
		
        RebootInstancesRequest request = new RebootInstancesRequest().withInstanceIds(instance_id);

        RebootInstancesResult response = ec2.rebootInstances(request);

        System.out.printf("Successfully rebooted instance %s", instance_id);
		
	}
	
	//8. listImages
	public static void listImages()
	{
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
		System.out.println("Listing images....");
		DescribeImagesRequest request = new DescribeImagesRequest().withOwners("self");

		DescribeImagesResult iresponse = ec2.describeImages(request);

		for (Image Im : iresponse.getImages()) 
		{
			System.out.printf("[ImageID] %s, [Name] %s, [Owner] %s \n", Im.getImageId(), Im.getName(),Im.getOwnerId());
	    }
	}
	
	//9. monitorInstance
	public static void monitorInstance()
	{
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
		
		System.out.println("Enter instance iD : ");
        Scanner scan = new Scanner(System.in);
        //String instance_id = scan.nextLine();
        final String instance_id = scan.nextLine();
        
		//here
        DryRunSupportedRequest<MonitorInstancesRequest> dry_request = new DryRunSupportedRequest<MonitorInstancesRequest>() {
			public Request<MonitorInstancesRequest> getDryRunRequest() {
            MonitorInstancesRequest request = new MonitorInstancesRequest()
                .withInstanceIds(instance_id);

            return request.getDryRunRequest();
            }
		};

        DryRunResult dry_response = ec2.dryRun(dry_request);

        if (!dry_response.isSuccessful()) {
            System.out.printf(
                "Failed dry run to enable monitoring on instance %s",
                instance_id);

            throw dry_response.getDryRunResponse();
        }

		//here

		
        MonitorInstancesRequest request = new MonitorInstancesRequest().withInstanceIds(instance_id);

        ec2.monitorInstances(request);

        System.out.printf("Successfully enabled monitoring for instance %s",instance_id);
    }
	
	//10. unmonitorInstance
	public static void unmonitorInstance()
	{
		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();
        System.out.println("Enter instance iD : ");
        Scanner scan = new Scanner(System.in);
        //String instance_id = scan.nextLine();
        final String instance_id = scan.nextLine();
        
		DryRunSupportedRequest<UnmonitorInstancesRequest> dry_request = new DryRunSupportedRequest<UnmonitorInstancesRequest>() {
			public Request<UnmonitorInstancesRequest> getDryRunRequest() {
			UnmonitorInstancesRequest request = new UnmonitorInstancesRequest()
			    .withInstanceIds(instance_id);

			return request.getDryRunRequest();
			}
		};

	        DryRunResult dry_response = ec2.dryRun(dry_request);

	        if (!dry_response.isSuccessful()) {
	            System.out.printf(
	                "Failed dry run to disable monitoring on instance %s",
	                instance_id);

	            throw dry_response.getDryRunResponse();
	        }

	    UnmonitorInstancesRequest request = new UnmonitorInstancesRequest().withInstanceIds(instance_id);
	    ec2.unmonitorInstances(request);

	    System.out.printf("Successfully disabled monitoring for instance %s",instance_id);
	    
	}
	
	//11. listKeypair
	public static void listKeypair()
	{
        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        DescribeKeyPairsResult response = ec2.describeKeyPairs();

        for(KeyPairInfo key_pair : response.getKeyPairs()) 
        {
            System.out.printf(
                "Found key pair with name [%s] " +
                "and fingerprint [%s]\n",
                key_pair.getKeyName(),
                key_pair.getKeyFingerprint());
        }
	}
	
	//12. createKeypair
	public static void createKeypair()
	{
        final String USAGE =
                "To run this example, supply a key pair name\n" +
                "Ex: CreateKeyPair <key-pair-name>\n";

            //if (args.length != 1) {
            //    System.out.println(USAGE);
            //    System.exit(1);
            //}

            //String key_name = args[0];
        System.out.println("Enter key name : ");
        Scanner scan = new Scanner(System.in);
        String key_name = scan.nextLine();

        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        CreateKeyPairRequest request = new CreateKeyPairRequest().withKeyName(key_name);

        CreateKeyPairResult response = ec2.createKeyPair(request);

        System.out.printf("Successfully created key pair named %s",key_name);	
	}
	
	//13. deleteKeypair
	public static void deleteKeypair()
	{
        final String USAGE =
                "To run this example, supply a key pair name\n" +
                "Ex: DeleteKeyPair <key-pair-name>\n";

            //if (args.length != 1) {
            //    System.out.println(USAGE);
            //    System.exit(1);
            //}

            //String key_name = args[0];
        System.out.println("Enter key name : ");
        Scanner scan = new Scanner(System.in);
        String key_name = scan.nextLine();

        final AmazonEC2 ec2 = AmazonEC2ClientBuilder.defaultClient();

        DeleteKeyPairRequest request = new DeleteKeyPairRequest().withKeyName(key_name);

        DeleteKeyPairResult response = ec2.deleteKeyPair(request);

        System.out.printf("Successfully deleted key pair named %s", key_name);
		
	}
	
	
}
