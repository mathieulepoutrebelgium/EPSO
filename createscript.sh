#!/bin/bash
SCRIPT_NAME=$(basename "$0")

# -h || --help
if [[ "$1" == '-h' || "$1" == '--help' ]]; then
    echo "-h | --help"
    echo "$SCRIPT_NAME <Client Comapny>"
    exit 0
fi
if [[ "$1" == '' ]]; then
    echo -e "$SCRIPT_NAME -h | --help\nOR\n$SCRIPT_NAME <Client Comapny>"
    exit 1
fi

parentDir=$1

# Check if the provided directory exists, if not create it
if [ ! -d "$parentDir" ]; then
    echo "The specified directory does not exist, creating it now: $parentDir"
    mkdir -p "$parentDir"
else
    echo "Directory '$parentDir' already exists, please give a uniqe name."
fi

# Define the options in an array
options=("Jira Cloud" "Jira OnPrem" "Zendesk" "Salesforce" "Azure DevOps" "ServiceNow" "GitHub")

# Function to display options
displayOptions() {
    echo "Please choose an option by entering the corresponding number:"
    for i in "${!options[@]}"; do
        echo "$((i + 1))) ${options[$i]}"
    done
}

# Function to create directory based on choice
createDirectory() {
    local dirName=""
    case $1 in
        1) dirName="JiraCloud";;
        2) dirName="JiraOnPrem";;
        3) dirName="Zendesk";;
        4) dirName="Salesforce";;
        5) dirName="AzureDevOps";;
        6) dirName="ServiceNow";;
        7) dirName="GitHub";;
        *) echo "Invalid option. Directory cannot be created."; return;;
    esac

    # Create directory inside the provided parent directory, handle duplicates
    local basePath="$parentDir/$dirName"
    local fullPath="$basePath"
    local count=1

    while [ -d "$fullPath" ]; do
        let count++
        fullPath="${basePath}${count}"
    done

    echo "Creating directory: $fullPath"
    mkdir "$fullPath"

    # List of files
    local files=("incoming.groovy" "outgoing.groovy")
    touch "$parentDir/Info.txt" "$parentDir/Triggers.txt"
    # Loop over the list and create each file
    for file in "${files[@]}"; do
        touch "$fullPath/$file"
    done
}

echo "You will now select 2 connectors."

# Array to hold user choices
userChoices=()

# Loop to get user input for two choices
for i in {1..2}; do
    while true; do
        displayOptions
        read -rp "Enter your choice (1-${#options[@]}): " choice
        # Check if the choice is within the valid range and not already chosen
        if [[ $choice =~ ^[1-7]$ ]]; then
            echo "Connector selected: ${options[$((choice - 1))]}"
            userChoices+=("$choice")
            break
        else
            echo "Invalid selection. Please try again."
        fi
    done
    clear
done

# Create directories for the selected options
for choice in "${userChoices[@]}"; do
    createDirectory $choice
done


