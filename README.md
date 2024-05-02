This project was writed in Java 17.0.10 and SpringBoot 3.2.5.
Database is postgreSQL and used docker compose and liquibase for create a db. 
Json file is located in resources folder for upload data.
You can upload jsonfile for to fill database by multipart file and downloading it in csv.

Example:

Input json file:

     {
      "label": "Merge Records",
      "genre": "Indie rock",
      "group": "Neutral Milk Hotel",
      "founded_year": "1989"
    }

Class GroupsAndLabels used for writting data from file.Class MusicGroupPojo and LabelsPojo is used for transfer data from parsers to main entities.

Output data in csv file:

       "Name","Genre","Label-id"
       "David Bowie","rock","9"
