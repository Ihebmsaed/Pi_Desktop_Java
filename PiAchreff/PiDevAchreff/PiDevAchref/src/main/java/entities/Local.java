

package entities;

public class Local {
    private int num;
    private String descript;
    private String lieu;
    private float surface;
    private int nbper;
    private int codec;
    private byte[] image;

    public Local(int num, String descript, String lieu, float surface, int codec, byte[] image) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.codec = codec;
        this.image = image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Local() {
    }

    public Local(int num, String descript, String lieu, float surface, int nbper, int codec, byte[] image) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.nbper = nbper;
        this.codec = codec;
        this.image = image;
    }

    public int getNum() {
        return this.num;
    }

    public String getDescript() {
        return this.descript;
    }

    public String getLieu() {
        return this.lieu;
    }

    public float getSurface() {
        return this.surface;
    }

    public int getNbper() {
        return this.nbper;
    }

    public int getCodec() {
        return this.codec;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public void setNbper(int nbper) {
        this.nbper = nbper;
    }

    public void setCodec(int codec) {
        this.codec = codec;
    }

    public String toString() {
        return "Local{num=" + this.num + ", descript=" + this.descript + ", lieu=" + this.lieu + ", surface=" + this.surface + ", nbper=" + this.nbper + ", codec=" + this.codec + ", image=" + this.image + '}';
    }
}
