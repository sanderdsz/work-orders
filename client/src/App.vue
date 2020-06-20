<template>
  <div id="app">
    <vue-snotify></vue-snotify>

    <nav class="navbar is-primary" role="navigation" aria-label="main navigation">
      <div class="navbar-brand">
        <a class="navbar-item">Betha - CRUD Ordem de Serviço</a>
      </div>
    </nav>

    <br />

    <div></div>

    <div class="container">
      <!-- Renderiza os erros capturados -->
      <ul>
        <li v-for="(error, index) of errors" :key="index">
          <div class="notification is-danger" style="margin-botton:10px">
            campo
            <b>{{error.field}}</b>
            - {{error.defaultMessage}}
          </div>
          <br />
        </li>
      </ul>

      <form @submit.prevent="salvar">
        <div class="field">
          <label class="label">Nome</label>
          <div class="control">
            <input class="input" type="text" placeholder="Nome" v-model="ordem.nome" />
          </div>
        </div>

        <div class="field">
          <label class="label">Endereço</label>
          <input class="input" type="text" placeholder="Endereço" v-model="ordem.endereco" />
        </div>

        <div class="field">
          <label class="label">Telefone</label>
          <input
            class="input"
            type="tel"
            placeholder="Telefone"
            v-mask="['(##) ####-####','(##) #####-####']"
            v-model="ordem.telefone"
          />
        </div>

        <div class="field">
          <label class="label">E-mail</label>
          <input class="input" type="text" placeholder="E-mail" v-model="ordem.email" />
        </div>

        <div class="field">
          <label class="label">Problema</label>
          <input
            class="input"
            type="text"
            placeholder="Descreva o ocorrido..."
            v-model="ordem.problema"
          />
        </div>

        <div class="field">
          <label class="label">Tipo</label>
          <input class="input" type="text" placeholder="Modelo de aparelho" v-model="ordem.tipo" />
        </div>

        <div class="field">
          <label class="label">Marca</label>
          <input class="input" type="text" placeholder="Marca" v-model="ordem.marca" />
        </div>

        <div class="control">
          <label class="label">Situação</label>
          <label class="radio">
            <input type="radio" value="Pendente" v-model="ordem.situacao" />
            Pendente
          </label>
          <label class="radio">
            <input type="radio" value="Finalizada" v-model="ordem.situacao" />
            Finalizada
          </label>
        </div>

        <br />
        <button class="button is-medium is-primary">Salvar</button>
      </form>
    </div>

    <br />

    <div>
      <div class="container">
        <div>
          <label class="label">Pesquisa</label>
          <input class="input" type="text" v-model="search" placeholder="Pendente ou Finalizada?" />
        </div>

        <br />

        <div class="columns is-centered">
          <div class="table-container">
            <table class="table is-striped" style="text-align:center">
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>Endereço</th>
                  <th>Telefone</th>
                  <th>E-mail</th>
                  <th>Problema</th>
                  <th>Tipo</th>
                  <th>Marca</th>
                  <th>Situação</th>
                  <th>Opções</th>
                </tr>
              </thead>

              <tbody>
                <tr v-for="ordem of filteredList" :key="ordem.id">
                  <td>{{ordem.nome}}</td>
                  <td>{{ordem.endereco}}</td>
                  <td>{{ordem.telefone}}</td>
                  <td>{{ordem.email}}</td>
                  <td>{{ordem.problema}}</td>
                  <td>{{ordem.tipo}}</td>
                  <td>{{ordem.marca}}</td>
                  <td>{{ordem.situacao}}</td>
                  <td>
                    <a class="button is-small is-warning" @click="editar(ordem)">Editar</a>
                    <a class="button is-small is-danger" @click="remover(ordem)">Delete</a>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <br />
      </div>
    </div>
  </div>
</template>

<script>
import Ordem from "./services/ordens";

export default {
  data() {
    return {
      ordem: {
        id: "",
        nome: "",
        endereco: "",
        telefone: "",
        email: "",
        problema: "",
        tipo: "",
        marca: "",
        situacao: ""
      },
      errors: [],
      ordens: [],
      search: ""
    };
  },

  mounted() {
    this.listar();
  },

  methods: {
    listar() {
      Ordem.listar()
        .then(response => {
          this.ordens = response.data;
        })
        .catch(e => {
          console.log(e);
        });
    },

    salvar() {
      if (!this.ordem.id) {
        Ordem.salvar(this.ordem)
          .then(response => {
            this.ordem = {};
            this.$snotify.success("Salvo com sucesso!");
            this.listar();
            this.errors = {};
          })
          .catch(e => {
            this.errors = e.response.data.errors;
          });
      } else {
        Ordem.atualizar(this.ordem)
          .then(response => {
            this.ordem = {};
            this.$snotify.info("Atualizado com sucesso!");
            this.listar();
            this.errors = {};
          })
          .catch(e => {
            this.errors = e.response.data.errors;
          });
      }
    },

    editar(ordem) {
      this.ordem = ordem;
    },

    remover(ordem) {
      if (
        this.$snotify.confirm("Aceita deletar?", {
          buttons: [
            {
              text: "Sim",
              action: toast => {
                Ordem.apagar(ordem);
                this.$snotify.remove(toast.id);
                this.listar();
                this.$snotify.error("Deletado com sucesso!");
              }
            },
            {
              text: "Não",
              action: toast => {
                console.log("Clicked: Não");
                this.$snotify.remove(toast.id);
              }
            }
          ]
        })
      );
    }
  },

  computed: {
    filteredList() {
      return this.ordens.filter(ordem => {
        return (
          ordem.situacao.toLowerCase().includes(this.search.toLowerCase()) ||
          ordem.nome.toLowerCase().includes(this.search.toLowerCase()) ||
          ordem.endereco.toLowerCase().includes(this.search.toLowerCase()) ||
          ordem.telefone.toLowerCase().includes(this.search.toLowerCase()) ||
          ordem.email.toLowerCase().includes(this.search.toLowerCase()) ||
          ordem.problema.toLowerCase().includes(this.search.toLowerCase()) ||
          ordem.tipo.toLowerCase().includes(this.search.toLowerCase()) ||
          ordem.marca.toLowerCase().includes(this.search.toLowerCase())
        );
      });
    }
  }
};
</script>

<style>
</style>
