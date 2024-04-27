public class WorldNoise {

    Pair size;
    Pair spawn;
    public double[][] noise;

    public WorldNoise(Pair size, Pair spawn){
        this.size = size;
        this.spawn = spawn;
        this.noise = new double[(int) size.x / 100 ][(int) size.y / 100 ];
    }

    public static void main(String[] args){

        WorldNoise worldNoise = new WorldNoise(new Pair(5,5), new Pair(0,0));

        worldNoise.generateNoise();

        for(int i = 0; i < worldNoise.noise.length; i++){
            for(int j = 0; j < worldNoise.noise.length; j++){
                System.out.print(worldNoise.noise[i][j]);
            }
            System.out.println();
        }

    }

    public void generateNoise(){
        
        SimplexNoise noiseGenerator = new SimplexNoise();

        for(int i = 0; i < noise.length; i++){
            for(int j = 0; j < noise.length; j++){
                noise[i][j] = noiseGenerator.noise(i, j);
            }
        }

    }

    public boolean checkForPlanet(int i, int j){
        return(noise[i][j] > 0.9);
    }

    public boolean checkOverlap(int i, int j){
         return (noise[i][j] > 0.9);
    }

}
    

