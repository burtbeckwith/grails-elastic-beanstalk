import com.amazonaws.auth.*

/**
* @author Kenneth Liu
*/


target(loadAwsCredentials: 'Load AWS credentials from a file or from env') {
	def credentials = getAwsCredentialsFromPropertiesFile()
	if (!credentials) credentials = getAwsCredentialsFromSystemProperties()
	awsCredentials = credentials //set global property
}

target(getServiceEndpoint: 'Read the default Elastic Beanstalk service endpoint ') {
	ebServiceEndpoint = '' //TODO finish this
	//ELASTICBEANSTALK_URL
	//config file
}

/**
* @return null if properties not found
*/
private AWSCredentials getAwsCredentialsFromSystemProperties() {
	def accessKey = System.getProperty('AWSAccessKeyId')
	def secretKey = System.getProperty('AWSSecretKey')
	println 'Loading credentials from System properties'

	if (!accessKey || !secretKey) return null //TODO log helpful error message

	new BasicAWSCredentials(accessKey, secretKey)
}

/**
* see: http://aws.amazon.com/articles/3586
* see: http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/usingCLI.html
*/
private AWSCredentials getAwsCredentialsFromPropertiesFile() {
	def credentialFile = System.getenv('AWS_CREDENTIAL_FILE')
	println 'Loading credential file from: ' + credentialFile

	if (!credentialFile) return null //TODO log helpful error message

	//TODO handle case where env var is set, but file is not there

	//new PropertiesCredentials(new File(credentialFile)) //TODO handle possible exceptions

	def props = new Properties()
	new File(credentialFile).withInputStream { props.load(it) }
	new BasicAWSCredentials(props.AWSAccessKeyId, props.AWSSecretKey)
}

private AWSCredentials getAwsCredentialsFromGrailsConfig() {

}
