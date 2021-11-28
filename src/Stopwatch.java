public class Stopwatch {

    private long startTime;
    private long stopTime;
    private boolean running;

    public Stopwatch(){
        running = false;
        startTime=0;
        stopTime=0;
    }

    public void Start(){
        if(!running)
        {
            running =true;
            startTime = System.currentTimeMillis();
        }
    }

    public int Stop(){
        if (running)
        {
            running =false;
            stopTime = System.currentTimeMillis();
        }
        return (int) (((stopTime - startTime)/1000F)/60F);
    }
}
