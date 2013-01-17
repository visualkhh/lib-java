package khh.hui.part.think;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Hashtable;

import khh.cast.CastMap_I;
import khh.hui.action.HuiAction;
import khh.hui.interfaces.HuiInterface;
import khh.std.Standard;
import khh.std.realworld.Info;

/* hashtable은 키값으로 널을 받지않고 sync safe된다
 * 기억을 대신한다
 */
public class HuiMemory<T> extends Hashtable<String, Info<T>> {
	protected  HuiMemory(){
	}
}
