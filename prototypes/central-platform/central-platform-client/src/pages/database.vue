<script lang="ts" setup>
import { OrganizationDTO } from "~/types/Organization/Organization";
import type { OrganizationAccessDTO } from "../types/OrganizationAccess/OrganizationAccess.ts";
import { useOrganizationDTOStore } from "~/stores/Organization/OrganizationStoreDTO";
import { useToast } from "vue-toastification";

definePageMeta({
  middleware: "auth",
});

const toast = useToast();

const organizationDTOStore = useOrganizationDTOStore();
const { getOrganizationDTOs } = organizationDTOStore;
const filteredOrganizations = computed(() => {
  if (ownOrganizationAccess.value) {
    const usedOrganizationIds = externalOrganizationAccesses.value.map(
      (access) => access.organization.id
    );
    const ownOrganizationId = ownOrganizationAccess.value.organization.id;

    return getOrganizationDTOs.value.filter((organization) => {
      return (
        !usedOrganizationIds.includes(organization.id) &&
        organization.id !== ownOrganizationId
      );
    });
  } else {
    setTimeout(() => {
      toast.error("Organization access entity not found.");
    }, 600);
  }
});

const ownOrganizationAccess = ref<OrganizationAccessDTO | null>(null);
const externalOrganizationAccesses = ref<OrganizationAccessDTO[]>([]);

const showModal = ref(false);
const selectedOrganization = ref<OrganizationDTO | null>(null);

onBeforeMount(async () => {
  setTimeout(async () => {
    await organizationDTOStore.fetchOrganizationDTOs();
  });
});

const openModal = () => {
  showModal.value = true;
};

const closeModal = () => {
  showModal.value = false;
};

const confirmSelection = async () => {
  if (selectedOrganization.value && selectedOrganization.value.id) {
    await addOrganizationAccess(selectedOrganization.value.id);
  } else {
    setTimeout(() => {
      toast.error("Organization was not found");
    }, 600);
  }

  showModal.value = false;
};
//TODO convert to store
async function fetchOwnOrganizationAccess() {
  const { data } = await useAsyncData<OrganizationAccessDTO>(() =>
    $fetch("http://localhost:8080/api/admin/organization-access/own", {
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
    ownOrganizationAccess.value = data.value;
  }
}
//TODO convert to store
async function fetchExternalOrganizationAccesses() {
  const { data } = await useAsyncData<OrganizationAccessDTO[]>(() =>
    $fetch("http://localhost:8080/api/admin/organization-access/external", {
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
    externalOrganizationAccesses.value = data.value;
  }
}
//TODO convert to store
async function removeOrganizationAccess(
  accessId: string,
  organizationId: string
) {
  const { data } = await useAsyncData<string>(() =>
    $fetch(
      "http://localhost:8080/api/admin/organization-access/remove/" +
        organizationId,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + useCookie("userToken").value,
        },
        body: {
          type: "delete",
        },
      }
    )
  );
  if (data.value) {
    const updatedOrganizationAccesses = externalOrganizationAccesses.value.filter(
      (access) => access.id !== accessId
    );
    externalOrganizationAccesses.value = updatedOrganizationAccesses;
  }
}
//TODO convert to store
async function addOrganizationAccess(organizationId: string) {
  const { data } = await useAsyncData<string>(() =>
    $fetch("http://localhost:8080/api/admin/organization-access/add", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + useCookie("userToken").value,
      },
      body: {
        type: "create",
        organizationId: organizationId,
      },
    })
  );

  if (data.value) {
    await fetchExternalOrganizationAccesses();
  }
}

//TODO convert to store
if (useCookie("userRole").value == "ADMIN") {
  await fetchOwnOrganizationAccess();
  await fetchExternalOrganizationAccesses();
}
</script>

<template>
  <div v-if="useCookie('userRole').value == 'ADMIN'" class="text-white">
    <h1 class="pl-5 mt-5 text-lg">Data Access</h1>
    <div v-if="ownOrganizationAccess" class="pl-5 pr-5 mt-8 mb-12">
      <div class="pl-5 bg-neutral-800 rounded-lg p-4">
        <h2 class="text-lg text-emerald">Own Organization Access:</h2>
        <p>
          <strong>Organization Name:</strong>
          {{ ownOrganizationAccess.organization.name }}
        </p>
        <p class="mb-4">
          <i>Organization ID:</i> {{ ownOrganizationAccess.organization.id }}
        </p>
        <p class="mb-2">
          <strong>Access given by the following organizations:</strong>
        </p>
        <ul class="bg-neutral-700 rounded pt-3 pl-2 pr-2 pb-1">
          <li
            v-for="org in ownOrganizationAccess.allowedOrganizations"
            :key="org.id"
            class="bg-neutral-800 rounded p-2 mb-2"
          >
            <p>{{ org.name }}</p>
            <p><i>Organization ID:</i> {{ org.id }}</p>
          </li>
        </ul>
      </div>
    </div>

    <div class="min-h-[400px] overflow-y-auto">
      <h2 class="pl-5 mt-3 text-md text-emerald">
        External Organizations that received access:
      </h2>
      <div class="ml-5 mt-2">
        <button
          class="border border-emerald-500 text-emerald-500 hover:bg-emerald-500 hover:text-white px-4 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 transition-colors duration-300"
          @click="openModal"
        >
          Add
        </button>

        <div
          v-if="showModal"
          class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 backdrop-filter backdrop-blur-lg"
        >
          <div class="bg-neutral-800 p-4 rounded-md shadow-lg">
            <div class="p-6 bg-neutral-700 rounded">
              <h2 class="text-lg font-bold mb-4">Select Organization</h2>
              <select
                v-model="selectedOrganization"
                class="block w-full border border-gray-300 p-2 rounded-md mb-4 text-black"
              >
                <option
                  v-for="organization in filteredOrganizations"
                  :value="organization"
                  class="text-black"
                >
                  {{ organization.name }}
                </option>
              </select>
              <div class="flex justify-end">
                <button
                  @click="confirmSelection"
                  class="px-4 py-2 bg-emerald-500 text-white rounded-md mr-2"
                >
                  Confirm
                </button>
                <button
                  @click="closeModal"
                  class="px-4 py-2 border border-gray-300 rounded-md"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div
        v-if="externalOrganizationAccesses.length > 0"
        class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 p-5"
      >
        <div
          v-for="extAccess in externalOrganizationAccesses"
          :key="extAccess.id"
          class="bg-neutral-800 rounded-lg p-4"
        >
          <div class="bg-neutral-900 h-100 p-4 rounded">
            <p>
              {{ extAccess.organization.name }}
            </p>
            <p>
              <i>Organization ID:</i> <br />
              {{ extAccess.organization.id }}
            </p>
            <div class="flex space-x-4 mt-8 mb-2 rounded pb-2">
              <button
                class="border border-red-500 text-red-500 hover:bg-red-500 hover:text-white px-4 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500 transition-colors duration-300"
                @click="
                  removeOrganizationAccess(
                    extAccess.id,
                    extAccess.organization.id
                  )
                "
              >
                Remove
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div v-else>
    <h1 class="pl-5 mt-5 text-lg">
      Organization Access insights require admin privileges.
    </h1>
  </div>
</template>
