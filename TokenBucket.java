public class TokenBucket {
    private final long maxBucketSize;
    private final long refillRate;

    private double currentBucketSize;
    private long lastRefillTimestamp;

    public TokenBucket(long maxBucketSize, long refillRate){
        this.maxBucketSize = maxBucketSize;
        this.refillRate = refillRate;

        currentBucketSize = maxBucketSize;
        lastRefillTimestamp = System.currentMillis();
    }

    public synchronized boolean allowRequest(int tokens){
        refill();

        if(currentBucketSize > tokens){
            currentBucketSize -= tokens;
            return true;
        }

        return false;
    }

    private void refill(){
        long now = Sustem.currentMillis();
        double tokensToAdd = (now - lastRefillTimestamp) * refillRate;
        currentBucketSize = Math.min(currentBucketSize + tokensToAdd, maxBucketSize);
        lastRefillTimestamp = now;
    }
}