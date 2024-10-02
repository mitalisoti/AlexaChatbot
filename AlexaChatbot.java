import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class AlexaChatbot {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Alexa Help Chatbot!");
        System.out.println("Type 'exit' to end the conversation.");

        Chatbot chatbot = new Chatbot();
        chatbot.start(scanner);
    }
}

class Chatbot {
    private final IssueResolver issueResolver;
    private final List<String> conversationHistory;

    public Chatbot() {
        issueResolver = new IssueResolver();
        conversationHistory = new ArrayList<>();
    }

    public void start(Scanner scanner) {
        String userInput = "";
        displayMenu();

        while (true) {
            System.out.print("You: ");
            userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Chatbot: Goodbye! Hope I could help you.");
                break;
            }

            if (userInput.equalsIgnoreCase("history")) {
                displayHistory();
                continue;
            }

            conversationHistory.add("You: " + userInput);
            respondToUser(userInput);
        }
    }

    private void displayMenu() {
        System.out.println("\nWhat do you need help with?");
        System.out.println("1. Alexa not responding");
        System.out.println("2. Wi-Fi connection issues");
        System.out.println("3. Volume adjustment");
        System.out.println("4. Enabling skills");
        System.out.println("5. Updating Alexa");
        System.out.println("6. Setting up a device");
        System.out.println("7. General troubleshooting");
        System.out.println("8. Echo Dot issues");
        System.out.println("9. Play music");
        System.out.println("10. Schedule a call with our representative");
        System.out.println("Type 'history' to view conversation history.");
        System.out.println("You can also type your question or choose a number.");
    }

    private void respondToUser(String input) {
        input = input.toLowerCase();
        Map<String, Integer> keywordCount = new HashMap<>();

        // Count occurrences of keywords for each issue
        countKeywords(input, issueResolver.alexaNotRespondingKeywords(), keywordCount, "alexaNotResponding");
        countKeywords(input, issueResolver.wifiIssuesKeywords(), keywordCount, "wifiIssues");
        countKeywords(input, issueResolver.adjustVolumeKeywords(), keywordCount, "adjustVolume");
        countKeywords(input, issueResolver.enablingSkillsKeywords(), keywordCount, "enablingSkills");
        countKeywords(input, issueResolver.checkForUpdatesKeywords(), keywordCount, "checkForUpdates");
        countKeywords(input, issueResolver.setupDeviceKeywords(), keywordCount, "setupDevice");
        countKeywords(input, issueResolver.generalTroubleshootingKeywords(), keywordCount, "generalTroubleshooting");
        countKeywords(input, issueResolver.echoDotIssuesKeywords(), keywordCount, "echoDotIssues");
        countKeywords(input, issueResolver.playMusicKeywords(), keywordCount, "playMusic");
        countKeywords(input, issueResolver.scheduleACallKeywords(), keywordCount, "scheduleACall");


        // Determine the issue with the highest count
        String mostRelevantIssue = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : keywordCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostRelevantIssue = entry.getKey();
            }
        }

        // Respond to the most relevant issue
        if (mostRelevantIssue != null) {
            switch (mostRelevantIssue) {
                case "alexaNotResponding":
                    issueResolver.alexaNotResponding();
                    break;
                case "wifiIssues":
                    issueResolver.wifiIssues();
                    break;
                case "adjustVolume":
                    issueResolver.adjustVolume();
                    break;
                case "enablingSkills":
                    issueResolver.enablingSkills();
                    break;
                case "checkForUpdates":
                    issueResolver.checkForUpdates();
                    break;
                case "setupDevice":
                    issueResolver.setupDevice();
                    break;
                case "generalTroubleshooting":
                    issueResolver.generalTroubleshooting();
                    break;
                case "echoDotIssues":
                    issueResolver.echoDotIssues();
                    break;
                case "playMusic":
                    issueResolver.playMusic();
                    break;
                case "scheduleACall":
                    issueResolver.scheduleACall();
                    break;
            }
        } else {
            System.out.println("Chatbot: I'm sorry, I didn't understand that. Please try asking about common issues with Alexa or refer to the menu.");
            displayMenu();
        }
    }

    private void countKeywords(String input, Set<String> keywords, Map<String, Integer> keywordCount, String issueKey) {
        int count = 0;
        for (String keyword : keywords) {
            if (input.contains(keyword)) {
                count++;
            }
        }
        keywordCount.put(issueKey, count);
    }

    private void displayHistory() {
        if (conversationHistory.isEmpty()) {
            System.out.println("Chatbot: No conversation history available.");
        } else {
            System.out.println("Chatbot: Here is your conversation history:");
            for (String entry : conversationHistory) {
                System.out.println(entry);
            }
        }
    }
}

class IssueResolver {
    private static Scanner scanner = new Scanner(System.in);

    public Set<String> alexaNotRespondingKeywords() {
        return Set.of("not responding", "won't respond", "stopped working", "alexa isn't responding", "alexa not hearing");
    }

    public Set<String> wifiIssuesKeywords() {
        return Set.of("wifi", "internet", "connection", "no internet", "network issues");
    }

    public Set<String> adjustVolumeKeywords() {
        return Set.of("volume", "loudness", "sound", "increase volume", "decrease volume");
    }

    public Set<String> enablingSkillsKeywords() {
        return Set.of("skills", "enable skills", "add skills", "skill activation", "new skills");
    }

    public Set<String> checkForUpdatesKeywords() {
        return Set.of("update", "upgrade", "new version", "check for updates", "software update");
    }

    public Set<String> setupDeviceKeywords() {
        return Set.of("setup", "install", "configure", "add device", "device setup");
    }

    public Set<String> generalTroubleshootingKeywords() {
        return Set.of("troubleshoot", "fix", "problem", "issues", "not working");
    }

    public Set<String> echoDotIssuesKeywords() {
        return Set.of("echo dot", "dot", "device issues", "echo problems");
    }

    public Set<String> playMusicKeywords() {
        return Set.of("play music", "music", "listen to music", "play a song", "play audio");
    }
    public Set<String> scheduleACallKeywords() {
        return Set.of("schedule a call", "schedule", "talk", "call", "speak with");
    }

    public void alexaNotResponding() {
        System.out.println("Chatbot: If Alexa is not responding, try these steps:");
        System.out.println("1. Restart your device by unplugging it and plugging it back in.");
        System.out.println("2. Check if it's connected to power.");
        System.out.println("3. Make sure you’re using the wake word correctly.");

        askYesNoQuestion("Is it still not responding?");
        if (askYesNoQuestion("Have you reset it?")) {
            System.out.println("Chatbot: If the issue persists, consider contacting support.");
        } else {
            System.out.println("Chatbot: Great! If you have any more questions, just ask.");
        }
    }

    public void wifiIssues() {
        System.out.println("Chatbot: To resolve Wi-Fi issues:");
        System.out.println("1. Ensure your device is connected to Wi-Fi. Check the Alexa app under 'Devices'.");
        System.out.println("2. Restart your router.");
        System.out.println("3. Move the device closer to the router.");
        System.out.println("4. Check for any service outages in your area.");

        if (askYesNoQuestion("Are you still having issues with Wi-Fi?")) {
            System.out.println("Chatbot: Try forgetting the Wi-Fi network in the Alexa app and reconnecting.");
            if (askYesNoQuestion("Did you try reconnecting?")) {
                System.out.println("Chatbot: If it still doesn’t connect, consider resetting your router.");
            } else {
                System.out.println("Chatbot: Let me know if you need any further assistance!");
            }
        } else {
            System.out.println("Chatbot: Awesome! Let me know if you need help with anything else.");
        }
    }

    public void adjustVolume() {
        System.out.println("Chatbot: To adjust the volume:");
        System.out.println("1. Say 'Alexa, set the volume to 5' for a specific level.");
        System.out.println("2. Use the physical volume buttons on your Alexa device.");
        System.out.println("3. Adjust the volume in the Alexa app under 'Devices'.");

        if (askYesNoQuestion("Are you satisfied with the volume now?")) {
            System.out.println("Chatbot: Great! If you have any more questions, just ask.");
        } else {
            System.out.println("Chatbot: You can also try saying 'Alexa, increase the volume' or 'decrease the volume'.");
            if (askYesNoQuestion("Did that work?")) {
                System.out.println("Chatbot: Great! If you have any more questions, just ask.");
            } else {
                System.out.println("Chatbot: If it's still not right, check if the Do Not Disturb mode is turned on.");
            }
        }
    }

    public void enablingSkills() {
        System.out.println("Chatbot: To enable skills:");
        System.out.println("1. Open the Alexa app and go to 'Skills & Games'.");
        System.out.println("2. Browse or search for skills you want to enable.");
        System.out.println("3. Tap 'Enable' to add the skill to your Alexa.");

        if (askYesNoQuestion("Have you enabled the skill you wanted?")) {
            System.out.println("Chatbot: Awesome! Enjoy your new skills. Let me know if you need anything else.");
        } else {
            System.out.println("Chatbot: If you need help finding skills, you can search for specific categories in the app.");
            if (askYesNoQuestion("Are you looking for a specific type of skill?")) {
                System.out.println("Chatbot: You can explore categories like Games, Music, or Smart Home.");
            } else {
                System.out.println("Chatbot: Let me know if you have any more questions about skills.");
            }
        }
    }

    public void checkForUpdates() {
        System.out.println("Chatbot: To check for updates:");
        System.out.println("1. Your Alexa device usually updates automatically.");
        System.out.println("2. Check the Alexa app under 'Settings' for any available updates.");

        if (askYesNoQuestion("Have you checked for updates?")) {
            System.out.println("Chatbot: Good! Let me know if you need help with anything else.");
        } else {
            System.out.println("Chatbot: Please check for updates and see if that resolves your issue.");
        }
    }

    public void setupDevice() {
        System.out.println("Chatbot: To set up a new device:");
        System.out.println("1. Open the Alexa app and go to 'Devices'.");
        System.out.println("2. Tap on 'Add Device' and select the type of device.");
        System.out.println("3. Follow the on-screen instructions to complete the setup.");

        if (askYesNoQuestion("Did you successfully set up your device?")) {
            System.out.println("Chatbot: Awesome! Enjoy your new device. Let me know if you need anything else.");
        } else {
            System.out.println("Chatbot: If you have trouble, try restarting your Alexa device and the new device.");
        }
    }

    public void generalTroubleshooting() {
        System.out.println("Chatbot: For general troubleshooting:");
        System.out.println("1. Restart your Alexa device.");
        System.out.println("2. Make sure it is connected to the internet.");
        System.out.println("3. Check if there are any known issues with Alexa services.");

        if (askYesNoQuestion("Did this help resolve your issue?")) {
            System.out.println("Chatbot: Great! Let me know if you have any more questions.");
        } else {
            System.out.println("Chatbot: You can also try resetting your device to factory settings.");
        }
    }

    public void echoDotIssues() {
        System.out.println("Chatbot: For Echo Dot issues:");
        System.out.println("1. Check the power connection.");
        System.out.println("2. Ensure the device is connected to Wi-Fi.");
        System.out.println("3. Restart the device by unplugging it and plugging it back in.");

        if (askYesNoQuestion("Is your Echo Dot working now?")) {
            System.out.println("Chatbot: Awesome! Let me know if you need any more help.");
        } else {
            System.out.println("Chatbot: If problems persist, consider resetting your Echo Dot to factory settings.");
        }
    }

    public void playMusic() {
        System.out.println("Chatbot: To play music, you can say:");
        System.out.println("1. 'Alexa, play music'.");
        System.out.println("2. 'Alexa, play [song name]'.");
        System.out.println("3. 'Alexa, play [artist name]'.");

        if (askYesNoQuestion("Is music playing as expected?")) {
            System.out.println("Chatbot: Great! Enjoy your music!");
        } else {
            System.out.println("Chatbot: Make sure your music service is connected in the Alexa app.");
        }
    }

    private boolean askYesNoQuestion(String question) {
        System.out.print("Chatbot: " + question + " (yes/no): ");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("yes");
    }

    public void scheduleACall() {
        System.out.print("Chatbot: Please enter your phone number to schedule a call: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Please choose a time slot from the following options:");
        System.out.println("1. 10:00 AM - 12:00 PM");
        System.out.println("2. 1:00 PM - 3:00 PM");

        System.out.println("3. 4:00 PM - 6:00 PM");

        System.out.print("Chatbot: Please select a number for the preferred time slot: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.println("Chatbot: You've selected 10:00 AM - 12:00 PM. A representative will contact you during this time tomorrow.");
                break;
            case "2":
                System.out.println("Chatbot: You've selected 1:00 PM - 3:00 PM. A representative will contact you during this time tomorrow.");
                break;
            case "3":
                System.out.println("Chatbot: You've selected 4:00 PM - 6:00 PM. A representative will contact you during this time tomorrow.");
                break;
            default:
                System.out.println("Chatbot: Invalid option. Please try again.");
                scheduleACall();
                break;
        }
    }
}


