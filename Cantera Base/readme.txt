CSC280 - E3-3 Dear Diary with Spring Data JPA
=============================================

The diary now lives in MySQL. You don't write the SQL: Spring Data JPA (with Hibernate
underneath) turns your classes into tables and your method calls into SQL.

Starts from: E3-1 (the API) and E3-2 (the MySQL container).


RUN
  Open THIS folder in VS Code (File > Open Folder), not a folder above it: the run task and
  debug settings live in this folder's .vscode/, and the other examples have the same class names.
  Everything in Docker:   docker compose up --build
  Or the database in Docker and the API in VS Code (easier to debug):
    docker compose up -d db
    Ctrl+Shift+B > "Run Spring Boot"
  Stop: docker compose down     (add -v to wipe the database)
  Note: the startup test (mvnw test) needs the database running.


WHAT'S NEW
  pom.xml                       + spring-boot-starter-data-jpa, + mysql-connector-j
  application.properties        where the database is, user/password, ddl-auto, show-sql
  model/DiaryEntry, Mood        @Entity, @Id, @GeneratedValue, @Column
  data/DiaryEntryRepository     an INTERFACE that extends JpaRepository. No class: Spring writes it.
  data/MoodRepository           the same for moods
  data/MoodSeeder               adds the 4 moods when the table is empty (CommandLineRunner)
  (gone) DiaryEntryStore, MoodStore, the JSON files, nextId
  controllers                   use the repositories: save, findById, existsById, deleteById
  compose.yaml                  db + app; the app waits until MySQL is healthy and reaches it at "db"


STORE (E3-1)                  ->  REPOSITORY (E3-3)
  findAll()                       findAll()                      SELECT ... FROM diary_entry
  findById(id)                    findById(id)                   SELECT ... WHERE id=?
  add(text, moodId)               save(new DiaryEntry(...))      INSERT
  update(id, text, moodId)        findById, set..., save(entry)  UPDATE
  delete(id)                      existsById + deleteById        DELETE
  searchByText(s)                 findByTextContainingIgnoreCase(s)   a "derived query"
  countWithMood(id)               countByMoodId(id)                    another one


TRY IT
  1. Start it, then watch the terminal: "Hibernate: create table diary_entry ..."
     Compare it with the CREATE TABLE you wrote in E3-2.
  2. POST /api/entries {"text":"Saved by JPA","moodId":2}, then look in the terminal
     for "Hibernate: insert into diary_entry ..."
  3. docker exec -it csc280-mysql mysql -udiary -pdiarypass diary
     SELECT * FROM diary_entry;      your row is really in MySQL
  4. Restart the app: the entries are still there.
  JSON keys may come back in a different order than before. That's fine: JSON objects
  have no order. Rows have no guaranteed order either, unless you ask for one (E3-4).


SUBMIT
  E3-3: a zip of your project (no target/), a screenshot of a POST in Postman, and a
  screenshot of SELECT * FROM diary_entry showing that row.
