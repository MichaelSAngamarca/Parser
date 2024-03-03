import org.junit.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import static org.junit.Assert.*;

public class test {

    @Test
    public void testLexer() throws IOException {
        File tempFile = createTempFile("testIntegerAndString", "5 hello \n 5.23 \n 8.5 \n 7.77.78.1 \n My$NameIsMiguel");
        LinkedList<Token> list = new Lexer().Lex(tempFile.getAbsolutePath());
        assertEquals("NUMBER(5)", list.get(0).toString());
        assertEquals("WORD(HELLO)", list.get(1).toString());
        assertEquals("ENDOFLINE", list.get(2).toString());
        assertEquals("NUMBER(5.23)", list.get(3).toString());
        assertEquals("ENDOFLINE", list.get(4).toString());
        assertEquals("NUMBER(8.5)", list.get(5).toString());
        assertEquals("ENDOFLINE", list.get(6).toString());
        assertEquals("NUMBER(7.77)", list.get(7).toString());
        assertEquals("NUMBER(.78)", list.get(8).toString());
        assertEquals("NUMBER(.1)", list.get(9).toString());
        assertEquals("ENDOFLINE", list.get(10).toString());
        assertEquals("WORD(MY$)", list.get(11).toString());
        assertEquals("WORD(NAMEISMIGUEL)", list.get(12).toString());
    }
    @Test
    public void testSymbols() throws IOException {
        File tempFile = createTempFile("testSymbols", "+ - * / ( ) < > = >= <= <>");
        LinkedList<Token> list = new Lexer().Lex(tempFile.getAbsolutePath());
        assertEquals("PLUS", list.get(0).toString());
        assertEquals("MINUS", list.get(1).toString());
        assertEquals("TIMES", list.get(2).toString());
        assertEquals("DIVIED", list.get(3).toString());
        assertEquals("LPAREN", list.get(4).toString());
        assertEquals("RPAREN", list.get(5).toString());
        assertEquals("LESSTHAN", list.get(6).toString());
        assertEquals("GREATERTHAN", list.get(7).toString());
        assertEquals("EQUALS", list.get(8).toString());
        assertEquals("GREATOREQUAL",list.get(9).toString());
        assertEquals("LESSOREQUAL", list.get(10).toString());
        assertEquals("NOTEQUAL", list.get(11).toString());
    }

    @Test
    public void testKeyWordsAndLiterals() throws IOException {
        File tempFile = createTempFile("testkeywordsandliterals", "PRINT READ INPUT DATA GOSUB FOR TO STEP NEXT RETURN IF THEN FUNCTION WHILE END\n");
        LinkedList<Token> list = new Lexer().Lex(tempFile.getAbsolutePath());
        assertEquals("PRINT", list.get(0).toString());
        assertEquals("READ", list.get(1).toString());
        assertEquals("INPUT", list.get(2).toString());
        assertEquals("DATA", list.get(3).toString());
        assertEquals("GOSUB", list.get(4).toString());
        assertEquals("FOR", list.get(5).toString());
        assertEquals("TO", list.get(6).toString());
        assertEquals("STEP", list.get(7).toString());
        assertEquals("NEXT", list.get(8).toString());
        assertEquals("RETURN", list.get(9).toString());
        assertEquals("IF", list.get(10).toString());
        assertEquals("THEN", list.get(11).toString());
        assertEquals("FUNCTION", list.get(12).toString());
        assertEquals("WHILE", list.get(13).toString());
        assertEquals("END", list.get(14).toString());
        assertEquals("ENDOFLINE", list.get(15).toString());
    }

    @Test
    public void testCodeHandler() throws IOException {
        // Create a temporary file with some content
        String fileName = "tempFile.txt";
        String fileContent = "This is a test file content.";
        Files.write(Paths.get(fileName), fileContent.getBytes());
        // Create a CodeHandler instance
        CodeHandler codeHandler = new CodeHandler(fileName);
        // Test peek method
        assertEquals('T', codeHandler.peek(0));
        assertEquals('h', codeHandler.peek(1));
        assertEquals('i', codeHandler.peek(2));
        assertEquals('s', codeHandler.peek(3));

        assertThrows(IndexOutOfBoundsException.class, () -> codeHandler.peek(-1));  // Test out of bounds
        // Test peekString method
        assertEquals("This", codeHandler.peekString(4));
        assertEquals("This is a test", codeHandler.peekString(14));

        // Test swallow method
        codeHandler.swallow(6);
        assertEquals('s', codeHandler.getChar());  // Make sure index has moved
        // Test isDone method
        assertFalse(codeHandler.isDone());  // Not done yet
        codeHandler.swallow(fileContent.length() - 1);  // Move to the end
        assertTrue(codeHandler.isDone());  // Should be done now
    }

    @Test
    public void testParserWithWholeIntegers() throws IOException {
        File tempFile = createTempFile("testParser","6-3+(5+2)*2");
        LinkedList<Token> lexer = new Lexer().Lex(tempFile.getAbsolutePath());
        Parser parser = new Parser(lexer);
        Node result = parser.parse();
        assertEquals("((6 - 3) + ((5 + 2) * 2))", result.toString());
    }

    @Test
    public void testParserWithFloats() throws IOException {
        File tempFile = createTempFile("testParser", "5.5*3.2+(6.3-2.1)/2.5");
        LinkedList<Token> lexer = new Lexer().Lex(tempFile.getAbsolutePath());
        Parser parser = new Parser(lexer);
        Node result = parser.parse();
        assertEquals("((5.5 * 3.2) + ((6.3 - 2.1) / 2.5))", result.toString());
    }

    private File createTempFile(String fileName, String content) throws IOException {
        File tempFile = File.createTempFile(fileName, ".txt");
        tempFile.deleteOnExit();

        try(FileWriter writer = new FileWriter(tempFile)){
            writer.write(content);

        }
        return tempFile;

    }



}
