<script lang="ts" setup>
import type { UserOrganizationApplication } from "../types/UserOrganizationApplication/UserOrganizationApplication.ts";

definePageMeta({
  middleware: "auth",
});

const userOrganizationApplications = ref<UserOrganizationApplication[]>([]);

//TODO convert to store
async function fetchUserOrganizationApplications() {
  const { data } = await useAsyncData<UserOrganizationApplication[]>(
    "userOrganizationApplications",
    () =>
      $fetch("http://localhost:8080/api/admin/user-assign-request", {
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
    userOrganizationApplications.value = data.value;
  }
}

function formattedDate(dateArray: number[]) {
  const date = new Date(
    dateArray[0],
    dateArray[1] - 1,
    dateArray[2],
    dateArray[3],
    dateArray[4],
    dateArray[5]
  );
  return date.toLocaleString("en-NL");
}

//TODO convert to store
async function updateUserOrganizationApplication(
  newStatus: string,
  userOrganizationApplication: UserOrganizationApplication
) {
  userOrganizationApplication.status = newStatus;

  await useAsyncData<UserOrganizationApplication[]>(
    "updateUserOrganizationApplication",
    async () =>
      await $fetch(
        "http://localhost:8080/api/admin/user-assign-request/update/" +
          userOrganizationApplication.id,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + useCookie("userToken").value,
          },
          body: {
            type: "update",
            userOrganizationApplication: userOrganizationApplication,
          },
        }
      )
  );

  const updatedApplications = userOrganizationApplications.value.filter(
    (application) => application.id !== userOrganizationApplication.id
  );
  userOrganizationApplications.value = updatedApplications;
}

//TODO convert to store
if (useCookie("userRole").value == "ADMIN") {
  await fetchUserOrganizationApplications();
}
</script>

<template>
  <div v-if="useCookie('userRole').value == 'ADMIN'" class="text-white">
    <h1 class="pl-5 mt-5 text-lg">User organization applications</h1>
    <div class="grid grid-cols-2 gap-1">
      <div
        v-if="userOrganizationApplications.length > 0"
        v-for="(
          userOrganizationApplication, index
        ) in userOrganizationApplications"
        :key="index"
        class="p-5 bg-neutral-900 rounded-lg text-white"
      >
        <div class="p-3 bg-neutral-800 rounded-lg text-white">
          <p class="mt-2 p-2 bg-neutral-700 rounded-lg">
            User email: {{ userOrganizationApplication.email }}
          </p>
          <p class="mt-2 p-2 bg-neutral-700 rounded-lg">
            Created at:
            {{ formattedDate(userOrganizationApplication.createdAt) }}
          </p>
          <p class="mt-2 p-2 bg-neutral-700 rounded-lg">
            Updated at:
            {{ formattedDate(userOrganizationApplication.updatedAt) }}
          </p>
          <div class="flex space-x-4 mt-12 mb-2 bg-neutral-800 rounded pb-2">
            <button
              class="border border-emerald-500 text-emerald-500 hover:bg-emerald-500 hover:text-white px-4 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-green-500 transition-colors duration-300"
              @click="
                updateUserOrganizationApplication(
                  'APPROVED',
                  userOrganizationApplication
                )
              "
            >
              Approve
            </button>
            <button
              class="border border-red-500 text-red-500 hover:bg-red-500 hover:text-white px-4 py-2 rounded-md focus:outline-none focus:ring-2 focus:ring-red-500 transition-colors duration-300"
              @click="
                updateUserOrganizationApplication(
                  'DENIED',
                  userOrganizationApplication
                )
              "
            >
              Decline
            </button>
          </div>
        </div>
      </div>
      <div class="text-white p-5" v-else>No applications at this moment.</div>
    </div>
  </div>

  <div class="text-white p-5">User information</div>
</template>
