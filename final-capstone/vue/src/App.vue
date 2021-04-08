<template>
  <div id="app">
    <link rel="preconnect" href="https://fonts.gstatic.com" />
    <link
      href="https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,300;0,400;0,600;1,400&display=swap"
      rel="stylesheet"
    />
    <header>
      <nav id="nav1">
        <router-link v-bind:to="{name: 'profile'}" v-show="$store.state.token != ''">Profile</router-link>
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

        <a href ="#">Playdates</a>
        <span v-show="$store.state.token != ''">&nbsp;|&nbsp;</span>
        <a href="#" v-show="$store.state.token != ''">Message Forum</a>
      </nav>
    </header>
    <router-view />
  </div>
</template>



<style>

/* * {
  border-style: dotted;
  border-color: red;
} */

#app {
  background-color: #FCF7FF;
}
header {
  display: flex !important;
  flex-direction: row;
  align-items: center; 
  justify-content: space-between;
  background-color: #07475f;
  color: #fcf7ff;
}
nav a {
  color: #fcf7ff !important;
  padding-left: 10px;
  padding-right: 10px;
}
header >nav, header>h1 {
  flex-basis: 50%
}
header>h1 {
  text-align: center;
}
header>nav {
  display: flex;
}
#nav2{
  justify-content: flex-end;
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

h1 {
  font-family: "Raleway", sans-serif;
}

input[type="text"],
input[type="password"],
input[type="number"],
input[type="url"] {
  width: 350px;
  margin: auto;
  margin-bottom: 5px;
}
</style>
