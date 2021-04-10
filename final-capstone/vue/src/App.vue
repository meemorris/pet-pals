<template>
  <div id="app">
    <head>
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,300;0,400;0,600;1,400&display=swap"
      rel="stylesheet"
    />
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Rock+Salt&display=swap" rel="stylesheet">
    </head>
    <header>
      <nav id="nav1">
        <router-link v-bind:to="{name: 'profile'}" v-show="$store.state.token != ''">Home</router-link>
        <router-link v-bind:to="{ name: 'home' }" v-show="$store.state.token == ''">Home</router-link
        >
        <span v-show="$store.state.token != ''">&nbsp;|&nbsp;</span>
        <router-link
          v-bind:to="{ name: 'logout' }"
          v-if="$store.state.token != ''"
          >Logout</router-link>

      </nav>
    

        <h1>Pet Pals</h1>

      <nav id="nav2">

        <router-link v-bind:to="{name: 'playdates'}">Playdates</router-link>
        <span v-show="$store.state.token != ''">&nbsp;|&nbsp;</span>
        <a href="#" v-show="$store.state.token != ''">Message Forum</a>
      </nav>
    </header>
    <router-view />
  </div>
</template>



<style>
body{
  color: #2a2b2a !important;
  /* background-color: #fcf7ff !important; */
}

/* * {
  border-style: dotted;
  border-color: red;
} */

#app {
  background-color: #FAFAFA; 
}
header {
  display: flex !important;
  flex-direction: row;
  align-items: center; 
  justify-content: space-between;
  background-color: #07475f;
  /* color: #fcf7ff !important; */
  color: #FAFAFA;
}
nav a {
  /* color: #fcf7ff !important; */
  color: #FAFAFA;
  padding-left: 10px;
  padding-right: 10px;
}

nav a:hover {
  text-decoration: none;
  color: #f89e7a;
}

#nav1 {
  margin-left: 20px;
}

header >nav, header>h1 {
  flex-basis: 50%;
}
header>h1 {
  text-align: center;
}
header>nav {
  display: flex;
}
#nav2{
  justify-content: flex-end;
  margin-right: 20px;
}

.btn {
  display: block !important;
  margin: auto;
  margin-top: 10px;
  background: #07475f !important;
  border-color: #07475f !important;
}

.btn:hover {
  background: #1e7c9e !important;
  border-color: #1e7c9e !important;
}

h1, h2, h3, h4 {
  font-family: "Raleway", sans-serif;
}

input[type="text"],
input[type="password"],
input[type="number"],
input[type="url"],
input[type="tel"],
input[type="email"] {
  width: 350px;
  margin: auto;
  margin-bottom: 5px;
}
</style>
