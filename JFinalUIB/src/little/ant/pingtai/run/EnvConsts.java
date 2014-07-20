package little.ant.pingtai.run;

public class EnvConsts
{
	/** dev 开发环境,sit,uat,pro 生产环境 */
	public static String	DEVMODE			= "dev";

	/**mysql postgresql*/
	public static String	DB_TYPE_KEY		= "mysql";													
	/** 默认为mysql 不需要 其它库要配置上*/
	public static String	DBDRIVERCLASS	= "";														
	public static String	DBURL			= "jdbc:mysql://sqld.duapp.com:4050/SRwrpcviTfJkPwSyhjeh";
	public static String	DBUSER			= "mNB2LkRsey2Q81h82BheB7TX";
	public static String	DBPASSWORD		= "Iln7Gc4IGC3Ra4rIQ8vVodU4vlKyo5UZ";

	/** 自动登录密钥 */
	public static String	UIBSECURITYKEY	= "ttJw6Oc4NEtwPf8CbmwLNQ==";
	/** 密码输入最大错误数 */
	public static int		PASSERRORCOUNT	= 6;
	public static int		PASSERRORHOUR	= 6;

	// ----------------------------------------
	public static String	host			= "bcs.duapp.com";
	public static String	accessKey		= "mNB2LkRsey2Q81h82BheB7TX";
	public static String	secretKey		= "Iln7Gc4IGC3Ra4rIQ8vVodU4vlKyo5UZ";
	public static String	bucket			= "testcqz1";
	// ----------------------------------------

	static
	{
		switch (DEVMODE)
		{
		// 开发环境常量
			case "dev":
				DBURL = "jdbc:mysql://127.0.0.1:3306/jfinaluib?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull";
				DBUSER = "root";
				DBPASSWORD = "";
				break;
			// sit环境常量
			case "sit":
				break;
			// uat环境常量
			case "uat":
				break;
			// 生产环境常量
			case "pro":
				DBURL = "jdbc:mysql://sqld.duapp.com:4050/SRwrpcviTfJkPwSyhjeh?characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&zeroDateTimeBehavior=convertToNull";
				DBUSER = "mNB2LkRsey2Q81h82BheB7TX";
				DBPASSWORD = "Iln7Gc4IGC3Ra4rIQ8vVodU4vlKyo5UZ";
				break;

		}

	}

}
