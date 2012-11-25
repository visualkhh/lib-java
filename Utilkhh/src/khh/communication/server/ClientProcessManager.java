package khh.communication.server;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import khh.communication.Communication_Interface;
import khh.reflection.ReflectionUtil;

public class ClientProcessManager
{

    private ArrayList<ClientProcess>    pool    = new ArrayList<ClientProcess>();

    public ClientProcessManager(EventQueue queue,Class executeclass, int size) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
    {
        for (int i = 0; i < size; i++)
            pool.add(new ClientProcess(queue,(Communication_Interface)ReflectionUtil.newClass(executeclass)));
        init();
    }

    public void init()
    {
//      Iterator<ClientProcess> iter = pool.iterator();
//      while (iter.hasNext())
//      {
//          Thread handler = (Thread) iter.next();
//          handler.start();
//      }
        for(int i = 0 ; i <pool.size();i++){
            ClientProcess handler = (ClientProcess) pool.get(i);//ClientSelector ->Thread
            handler.start();
        }
    }
}
