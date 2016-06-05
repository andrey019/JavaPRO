package HomeWork.Lesson3.HTTPServer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class CacheLifeController extends Thread {
    private ConcurrentHashMap<String, byte[]> map;
    private HashMap<String, Long> timeMap;
    private int maximumLifeTime = 60000;    // in milliseconds

    CacheLifeController(ConcurrentHashMap<String, byte[]> map) {
        this.map = map;
        timeMap = new HashMap<>();
    }

    CacheLifeController(ConcurrentHashMap<String, byte[]> map, int maximumLifeTime) {
        this.map = map;
        this.maximumLifeTime = maximumLifeTime;
        timeMap = new HashMap<>();
    }

    public void run() {
        while (!isInterrupted()) {
            checkCache();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkCache() {
        for (Map.Entry<String, byte[]> entry : map.entrySet()) {
            if (!timeMap.containsKey(entry.getKey())) {
                timeMap.put(entry.getKey(), System.currentTimeMillis());
            } else {
                if ( (System.currentTimeMillis() - timeMap.get(entry.getKey())) > maximumLifeTime ) {
                    timeMap.remove(entry.getKey());
                    map.remove(entry.getKey());
                    System.out.println("Removed from cache: " + entry.getKey());
                }
            }
        }
    }
}
