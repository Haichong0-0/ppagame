public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
    GO("go"), QUIT("quit"), HELP("help"),BACK("back"),FIGHT("fight"),SNEAK("sneak"),TAKE("take"),DROP("drop"),CHECK("check"),USE("use").Cl;
    //"go", "quit", "help","back","fight","sneak","take","drop","check","use","class"

    // The command string.
    private String commandString;

    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }

    /**
     * @return The command word as a string.
     */
    public String toString()
    {
        return commandString;
    }
}