import com.gpt.ChatBot;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ChatBot chatTest = new ChatBot();
        chatTest.promptTester();
    }
}
