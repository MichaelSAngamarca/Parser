import java.util.LinkedList;
import java.util.Optional;

public class TokenManager {
    private LinkedList<Token> linkedListOfTokens;

    /*/
    Constructor sets the LinkedList when initialized (Never should be assigned more than once)
     */
    public TokenManager(){
        linkedListOfTokens = new LinkedList<>();
    }

    /*/
    Similar to Code Handler, We take a integer j as the index we want to peek ahead in the list of tokens
    checking the condition of the parameter to see if the index is a non-zero value as well as less than the size of the list
    return the token ahead using the Optional Wrapper Class, if j is not a valid index we return Optional.empty
     */
    Optional<Token> Peek(int j){
        if(j > 0 && j < linkedListOfTokens.size()){
            return Optional.of(linkedListOfTokens.get(j));
        }
        return Optional.empty();
    }
    /*/
    MoreTokens checks the linked list and returns true if the linked list is not empty and false otherwise.
     */
    boolean MoreTokens(){
        return !linkedListOfTokens.isEmpty();
    }

    /*/
    MatchAndremove we first check the condtion and return an Optional Empty if the linked list is empty
    To Match compare token values we first create a variable of Token type of firstToken that is equal to the head of the linked list
    To compare the types we can modify the token class (See Token Class returnTokenType)
     */
    Optional<Token> MatchAndRemove(Token.TokenType t){
        if(!linkedListOfTokens.isEmpty() && linkedListOfTokens.getFirst().returnTokenType() == t){
            return Optional.of(linkedListOfTokens.removeFirst());
        }
        return Optional.empty();


    }
}
