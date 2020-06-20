import { http } from "./config";

export default {
  listar: () => {
    return http.get("ordens");
  },

  salvar: ordem => {
    return http.post("ordem", ordem);
  },

  atualizar: ordem => {
    return http.put("ordem", ordem);
  },

  apagar: ordem => {
    return http.delete("ordem", { data: ordem });
  }
};
