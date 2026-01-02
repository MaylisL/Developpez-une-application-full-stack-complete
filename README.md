# MDD project

This project is a full-stack social network application for managing posts and subjects, built with **Angular 14** for the front-end and **Spring Boot** for the back-end.

## Technologies Used

- **Front-end**: Angular 14.1.3, Angular Material, RxJS  
- **Back-end**: Spring Boot 3, Spring Security (JWT), JPA/Hibernate  
- **Database**: MySQL  
- **Other**: Lombok, Javadoc for backend documentation, JWT for authentication

## Front-end

1. Clone the project.  
2. Go to the `front` folder 
3. Install dependencies with `npm install`
4. Run `ng serve` for a dev server.
5. Navigate to `http://localhost:4200/`. 
The application will automatically reload if you change any of the source files.
6. Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Back-end

1. Go to the `back` folder
2. Run `mvn spring-boot:run`
3. Environment Variables are used in this project. think about changing them in your IDE:
    - MYSQL_USERNAME
    - MYSQL_PASSWORD
    - JWT_KEY

4. The server runs at `http://localhost:8080/`

## Javadoc

Javadoc decorators have been added to the project.
To generate the documentation run `mvn javadoc:javadoc`
Open `target/site/apidocs/index.html` to explore services and methods

## Notes

* Reusable Angular components follow smart/dumb separation
* Buttons and cards are consistent with Angular Material styling
* JWT stored in localStorage and sent via interceptor
* HTTP requests automatically complete, no manual unsubscribe needed

## Workflow

We follow gitflow `https://nvie.com/posts/a-successful-git-branching-model/`

* Working branch is develop
* Each feature has to be developed on its own branche (ex: feature_auth)
* Feature branches should be created from branch develop
* When feature is done we merge it to develop
* When version is ready we release and do merge to main


