package pl.Bergmann.marviqproject.models.runtime;

public class RuntimeMachine
{
    private Long countRunning;
    private String machineName;

    public RuntimeMachine(Long countRunning, String machineName) {
        this.countRunning = countRunning;
        this.machineName = machineName;
    }

    public Long getCountRunning() {
        return countRunning;
    }

    public void setCountRunning(Long countRunning) {
        this.countRunning = countRunning;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
