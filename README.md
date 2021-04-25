[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![LinkedIn][linkedin-shield]][linkedin-url]



<!-- PROJECT LOGO -->
<br />


<h3 align="center">Github stalker - Allegro Intern</h3>

  <p align="center">
    The recruitment task number 3 for Allegro Summer e-Xperience 2021. <br />
 <br />
    <a href="https://github.com/szczygiel2000/allegro-intern-github-stalker/issues">Report Bug</a>
    ·
    <a href="https://github.com/szczygiel2000/allegro-intern-github-stalker/issues">Request Feature</a>
  </p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#how-to-run">How To Run</a>
        <ul>
            <li><a href="#from-terminal">From Terminal</a></li>
            <li><a href="#from-ide">From IDE</a></li>
            <li><a href="#with-dockerhub">With Dockerhub</a></li>
        </ul>
    </li>
    <li><a href="#api-documentation">API Documentation</a>
        <ul>
            <li><a href="#repos-endpoint">Repos Endpoint</a></li>
            <li><a href="#stargazers-endpoint">Stargazers Endpoint</a></li>
            <li><a href="#errors">Errors</a></li>
        </ul>
    </li>
    <li><a href="#future-development">Future Development</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

Server application that allows: <br />
- listing of repositories (name and number of stargazers) <br />
- returning the sum of stargazers in all repositories for any GitHub user.

### Built With

* [Java](https://java.com/)
* [Spring Boot](https://spring.io/)
* [Ehcache](https://www.ehcache.org)
* [Junit 5](https://junit.org/junit5/)
* [Swagger UI](https://swagger.io/)
* [Docker](https://www.docker.com)



<!-- GETTING STARTED -->

## How To Run

To quickly run local GitHubStalker server follow instructions bellow.

### From Terminal


1. Make sure you have gradle and java version 11 or higher installed.
2. Download this repository.
3. Make sure you have configured secrets in file. Instructions below.
4. Open any terminal in local project directory.
5. Build project with `./gradlew build`.
6. Run project with `./gradlew bootRun`.


### From IDE

<ol>
    <li>Clone this repository. You can do this manually or from IDE.</li>
    <li>Make sure you have configured secrets in file. Instructions below.</li>
    <li>Run project with IDE for example IntelliJ IDEA</li>
    <li>Wait until your IDE will index all files then run Gradle build task.</li>
    <li>Run project and enjoy.</li>
</ol>

### With Dockerhub

1. Pull the image from Dockerhub.
2. Open any terminal.
3. Pull the image from Dockerhub with:

```$ docker pull szczygiel2000/github-stalker-allegro-intern```

4. Run container with: 
   
```$ docker run -p 9090:8080 szczygiel2000/github-stalker-allegro-intern```



### App secrets in file

For not authorized requests GitHub Api permits only 60 requests per hour.
Application to work efficiently needs GitHub OAuth secret key, see the [Documentation](https://docs.github.com/en/github/authenticating-to-github/about-authentication-to-github) 
how to get it. In root folder of project update application.yaml
file, and paste bellow code with your own values to this file like this.

```yaml
application:
  github:
    authToken: '*** HERE GOES YOUR GITHUB OAUTH TOKEN ***'
    urlPrefix: 'https://api.github.com/users/'
spring:
  cache:
    jcache:
      config: 'classpath:ehcache.xml'
```

## Api Documentation

### Repos Endpoint:

* GET, path: **/repos/{user}**

It allows us listing user repos.

**Sample Request**:
```
curl -X GET "http://localhost:8080/api/v1/repos/szczygiel2000" -H "accept: */*"
```
**Sample Response**:
```json
{
  "user": "szczygiel2000",
  "user_repos": [
    {
      "name": "Balls-Simulator",
      "stargazers_count": 0
    },
    {
      "name": "Numeric_Matrix_Processor",
      "stargazers_count": 0
    },
    {
      "name": "PCBuilderShopApplication---Backend",
      "stargazers_count": 1
    },
    {
      "name": "Snake-Game",
      "stargazers_count": 0
    }
  ]
}
```
Http Status Code: 200 (OK)

**Pagination**

This endpoint also allows us to list user repos by page and size of page.

**Sample Request with Pagination**:

```
curl -X GET "http://localhost:8080/api/v1/repos/allegro?page=3&per_page=4" -H "accept: */*"
```
**Sample Response**:
```json
{
  "user": "allegro",
  "user_repos": [
    {
      "name": "atm-event-app",
      "stargazers_count": 5
    },
    {
      "name": "atm-event-scanner",
      "stargazers_count": 0
    },
    {
      "name": "axion-release-plugin",
      "stargazers_count": 380
    },
    {
      "name": "bigcache",
      "stargazers_count": 4793
    }
  ]
}
```
With Http Status Code: 200 (OK)

### Stargazers Endpoint 

* GET, path: **/stargazers/{user}**

Returning the sum of stargazers in all repositories for any GitHub user.

**Sample Request**:
```
curl -X GET "http://localhost:8080/api/v1/stargazers/szczygiel2000" -H "accept: */*"
```
**Sample Response**:
```json
{
  "user": "szczygiel2000",
  "totalStargazers": 1
}
```
With Http Status Code: 200 (OK)

**Another Sample Request**:
```
curl -X GET "http://localhost:8080/api/v1/stargazers/allegro" -H "accept: */*"
```
**Another Sample Response**:
```json
{
  "user": "allegro",
  "totalStargazers": 13104
}
```
With Http Status Code: 200 (OK)

### Errors

**Not Found**

If we try to stalk non-existing user we will get response like this:
```json
{
  "status": "404",
  "message": "User 'notAllegro' not found.",
  "timestamp": "2021-04-25T13:02:45.6842646"
}
```
With Http Status Code: 404 (NOT_FOUND)

**Bad Request**

On GitHub the shortest name is 1 character, while the longest - 39. 
This Api validates provided username and if it does not match return response just like this:
```json
{
  "status": "400",
  "message": "Username must be between 1 and 39 characters. Your is 45.",
  "timestamp": "2021-04-25T13:10:08.0273211"
}
```
With Http Status Code: 404 (BAD_REQUEST)

**Gateway Timeout**

If the server waits for a reply from GitHub for more than 5 seconds it will return:
```json
{
  "status": "504",
  "message": "GitHub wasn't responding for too long.",
  "timestamp": "2021-04-25T13:13:06.3558892"
}
```
With Http Status Code: 504 (GATEWAY_TIMEOUT)

## Future Development

- Improving the quality of tests by mocking GitHub responses. 
  Now tests are sending requests to GitHub Api and can't accurately test is returned values are correct.
  Now the tests are indicative only. If I had more time, I would certainly implement it.
- Sending parallel requests to GitHub Api while calculating total users stargazers to prevent long waiting for sum.
- Add sorting user repos by stargazers.
- Add some frontend to enable non-tech users to use the application.


<!-- LICENSE -->

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<!-- CONTACT -->

## Contact

Mateusz Szczygieł - [mszczygiel.contact@gmail.com](mailto:mszczygiel.contact@gmail.com) - Mail

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/szczygiel2000/allegro-intern-github-stalker.svg?style=for-the-badge

[contributors-url]: https://github.com/szczygiel2000/allegro-intern-github-stalker/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/szczygiel2000/allegro-intern-github-stalker.svg?style=for-the-badge

[forks-url]: https://github.com/szczygiel2000/allegro-intern-github-stalker/network/members

[stars-shield]: https://img.shields.io/github/stars/szczygiel2000/allegro-intern-github-stalker.svg?style=for-the-badge

[stars-url]: https://github.com/szczygiel2000/allegro-intern-github-stalker/stargazers

[issues-shield]: https://img.shields.io/github/issues/szczygiel2000/allegro-intern-github-stalker.svg?style=for-the-badge

[issues-url]: https://github.com/szczygiel2000/allegro-intern-github-stalker/issues

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/mateusz-szczygieł-502727197/
