package udx;

//import vertica SDK
import com.vertica.sdk.*;
public class VerticaWithElasticSearchFactory extends ScalarFunctionFactory {

	@Override
	public ScalarFunction createScalarFunction(ServerInterface arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPrototype(ServerInterface arg0, ColumnTypes arg1, ColumnTypes arg2) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Created the ScalarFunction as an inner class of its ScalarFunctionFactory class
	 * @author User
	 *
	 */
	
	public class VerticaWithElasticSearch extends ScalarFunction {

		@Override
		public void processBlock(ServerInterface arg0, BlockReader arg1, BlockWriter arg2)
				throws UdfException, DestroyInvocation {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	

}
