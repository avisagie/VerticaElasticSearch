package com.vastech.udx;

//import vertica SDK
import com.vertica.sdk.*;
public class VerticaWithElasticSearchFactory extends ScalarFunctionFactory {

	@Override
	public ScalarFunction createScalarFunction(ServerInterface arg0) {
		return new VerticaWithElasticSearch();
	}

	@Override
	public void getPrototype(ServerInterface serverInterface, ColumnTypes columnTypes, ColumnTypes returnType) {
		columnTypes.addInt();
		returnType.addInt();
		
	}
	
	/**
	 * Created the ScalarFunction as an inner class of its ScalarFunctionFactory class
	 * @author User
	 *
	 */
	
	public class VerticaWithElasticSearch extends ScalarFunction {
		
		
		long likes = 0;

		@Override
		public void processBlock(ServerInterface arg0, BlockReader arg1, BlockWriter arg2)
				throws UdfException, DestroyInvocation {
			
			do {
				long favCount = arg1.getLong(0);
				
				if(favCount == likes){
					arg2.setLong(favCount);
				}
				arg2.next();
			} while(arg1.next());
			
		}
		
	}
	
	

}
