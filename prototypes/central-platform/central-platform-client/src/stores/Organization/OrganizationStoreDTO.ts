import { defineStore } from "pinia";
import { useSessionStorage, type RemovableRef } from "@vueuse/core";
import type { OrganizationDTO } from "~/types/Organization/Organization";

export const useOrganizationDTOStore = defineStore("organizationDTOs", () => {
  // ref -> state -> data
  const organizationDTOs: RemovableRef<OrganizationDTO[]> = useSessionStorage<
    OrganizationDTO[]
  >("organizationDTOs", [] as OrganizationDTO[]);
  // computed -> getters -> access information
  const getOrganizationDTOs: ComputedRef<RemovableRef<OrganizationDTO[]>> = computed(
    () => organizationDTOs
  );

  // function -> mutattion -> do some kind of logic
  async function fetchOrganizationDTOs() {
    const { data } = await useAsyncData<OrganizationDTO[]>("organizationDTOs", () =>
      $fetch("http://localhost:8080/api/prod/organizationdto", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: {
          type: "info",
        },
      })
    );

    if (data.value) {
      organizationDTOs.value = data.value;
    }
  }

  return {
    getOrganizationDTOs,
    organizationDTOs,
    fetchOrganizationDTOs,
  };
});
