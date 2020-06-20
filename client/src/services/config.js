import axios from "axios";

export const http = axios.create({
  // Para utilizar servidor local, retirar o comentário abaixo, e comentar o parametro seguido;
  // baseURL: "http://localhost:8080/api/"
  baseURL: "https://betha-api.herokuapp.com/api/"
});
