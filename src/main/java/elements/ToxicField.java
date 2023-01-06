package elements;

public class ToxicField {
    public Vector2d position;
    public int deaths;

    public ToxicField(Vector2d position, int deaths){
        this.deaths = deaths;
        this.position = position;
    }
}
