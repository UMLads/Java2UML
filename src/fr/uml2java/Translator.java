package fr.uml2java;

import java.io.*;

public abstract class Translator {
    private File file;
    private BufferedReader reader;

    /**
     * // Translates the contents of a file
     * @exception IOException
     */
    public abstract void translate() throws IOException;

    /**
     * @return the reader
     */
    public BufferedReader getReader() {
        return this.reader;
    }

    /**
     * @param reader the reader to set the reader to
     */
    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    /**
     * @param fileName the file to set the file path to
     */
    public void setFile(String fileName) {
        this.file = new File(fileName);
    }

    /**
     * @param file the file to set the file to
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * //initializes the reader with the class attribute file
     * @exception FileNotFoundException
     */
    public void initializeReader() throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(this.getFile()));
    }

    public String getLine() throws IOException {
        return this.reader.readLine();
    }
}

//eof
