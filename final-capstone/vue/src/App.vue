<template>
  <div id="app">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Raleway:ital,wght@0,300;0,400;0,600;1,400&display=swap" rel="stylesheet">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>

<style>



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
  font-family: 'Raleway', sans-serif;
}

input[type=text], input[type=password] {
  width: 350px;
  margin: auto;
  margin-bottom: 5px;
}


</style>
