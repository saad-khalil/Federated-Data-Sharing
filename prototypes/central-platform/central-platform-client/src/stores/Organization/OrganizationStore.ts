import { defineStore } from "pinia";
import { useSessionStorage, type RemovableRef } from "@vueuse/core";
import type { Organization } from "~/types/Organization/Organization";

export const useOrganizationStore = defineStore("organizations", () => {
  // ref -> state -> data
  const organizations: RemovableRef<Organization[]> = useSessionStorage<
    Organization[]
  >("organizations", [] as Organization[]);
  // computed -> getters -> access information
  const getOrganizations: ComputedRef<RemovableRef<Organization[]>> = computed(
    () => organizations
  );

  // function -> mutattion -> do some kind of logic
  async function fetchOrganizations() {
    const { data } = await useAsyncData<Organization[]>("organizations", () =>
      $fetch("http://localhost:8080/api/prod/organization", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + useCookie("userToken").value,
        },
        body: {
          type: "info",
        },
      })
    );

    if (data.value) {
      organizations.value = data.value;
    }
  }

  function reset(){
    organizations.value = []
  }

  return {
    getOrganizations,
    organizations,
    fetchOrganizations,
    reset
  };
});
