package pl.Bergmann.marviqproject.models.common;

public enum StatusOfMachine
{
    Warning("Orange"),Fatal("Red"), Good("Green");

    public final String label;

    private StatusOfMachine(String label)
    {
        this.label = label;
    }
}
