// Import several useful classes
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

class Conversation implements ConversationRequirements {

  // Attributes 
  private ArrayList<String> transcript = new ArrayList<String>();
  private Random generator = new Random(); // For generating "canned" responses
  
  /**
   * Constructor 
   */
  public Conversation() {
    
  }

  /**
   * Starts and runs the conversation with the user
   */
  public void chat() {
    // Define variables
    Scanner input = new Scanner(System.in);
    int rounds;
    String userChat;

    // Get number of rounds from user
    System.out.println("How many rounds of conversation?");
    rounds = input.nextInt();

    // For some reason, it skips the first user input
    // The first time through the loop just prints the greeting
    for (int i = 0; i <= rounds; i++) {
      userChat = input.nextLine();

      if (i == 0) {
        // Greet user
        System.out.println("\nHello! What's up?");
        this.transcript.add("Hello! What's up?");
      }
      else {
        this.transcript.add(userChat);
        System.out.println(respond(userChat));
      }
    }

    // Say goodbye and close input
    input.close();
    System.out.println("Bye!");
    this.transcript.add("Bye!");
  }

  /**
   * Prints transcript of conversation
   */
  public void printTranscript() {
    System.out.println("\nTRANSCRIPT:"); // Transcript header
    // Print each line in the transcript in order
    for (int i = 0; i < transcript.size(); i++) {
      System.out.println(transcript.get(i));
    }
  }

  /**
   * Gives appropriate response (mirrored or canned) to user input
   * @param inputString the users last line of input
   * @return mirrored or canned response to user input  
   */
  public String respond(String inputString) {
    String[] mirrorWordsIn = {"I", "me", "am", "are", "you", "my", "your", "I'm", "you're", "I've", "you've", "I'd", "you'd"};
    String[] mirrorWordsOut = {"you", "you", "are", "am", "I", "your", "my", "you're", "I'm", "you've", "I've", "you'd", "I'd"};
    String[] cannedResponses = {"Mhm.", "Interesting!", "Oh, really?", "Got it.", "Okay."};
    String returnString = ""; 
    boolean mirror = false;

    // Check whether the response should be mirrored
    String[] words = inputString.split("[,\\.\\?\\!\\s]");
    for (int i = 0; i < words.length; i++) {
      if (Arrays.asList(mirrorWordsIn).contains(words[i])) {
        mirror = true;
        break;
      }
    }

    // split inputString by spaces (or other punctuation,
    // but KEEP THE OTHER PUNCTUATION as its own "word")
    // check by word for a match in mirrorWordsIn?
    // if match is found, mirror = true, then reconstruct string
    // w/ mirrored words and continue going through word by word
    // make sure to mirror caps and mirror "."/"?"
    // maybe mirror "!" to "?!"
    // (separate punctuation check in reconstruction --
    // don't want it to trigger mirroring)

    if (mirror) {
      // Constructs a mirrored string one word at a time
      // Currently does not include punctuation or capitalization
      boolean mirrorWord;
      for (int i = 0; i < words.length; i++) {
        mirrorWord = false;
        if ((i != 0) && (!(words[i].equals("")))) {
          returnString += " ";
        }

        for (int j = 0; j < mirrorWordsIn.length; j++) {
          if (words[i].equals(mirrorWordsIn[j])) {
            mirrorWord = true;
            returnString += mirrorWordsOut[j];
            break;
          }
        }
        if (!mirrorWord) {
          returnString += words[i];
        }
      }
    }
    else {
      // Return a random canned response
      int rand = this.generator.nextInt(cannedResponses.length);
      returnString = cannedResponses[rand];
    }

    this.transcript.add(returnString);
    return returnString; 
  }

  // Temporary debug method; see reflection.md for explanation
  public void addToTranscriptDebug(String transcriptLine) {
    this.transcript.add(transcriptLine);
  }

  public static void main(String[] arguments) {

    Conversation myConversation = new Conversation();
    myConversation.chat();
    // Temporary debug code to demonstrate that printTranscript() works in and of itself;
    // see reflection.md for explanation
    // To have printTranscript() work, uncomment these lines and comment out myConversation.chat();
    // myConversation.addToTranscriptDebug("Testing.");
    // myConversation.addToTranscriptDebug("Debug.");
    myConversation.printTranscript();

  }
}
