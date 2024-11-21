const USER_ENDPOINT = "http://localhost:8081/api/v1/users"

const GROUP_ENDPOINT = "http://localhost:8081/api/v1/groups"

export const getAllUsers = async () => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(USER_ENDPOINT,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        console.log(data);
        return data;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}

export const createGroup = async (name, description, trainerId, runnersId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(GROUP_ENDPOINT,{
        method: 'POST',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, description: description, trainerId: trainerId, runnersId: runnersId})
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then((errorData) => {
                throw { status: response.status, ...errorData };
            });
        }
        return response.json();
      })
      .then(data => {
        return data;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}

export const getAvailableGroups = async () => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${GROUP_ENDPOINT}/available`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        console.log(data);
        return data;
    })
    .catch(error => {
        console.error('Error:', error);
        throw error;
    });
}

export const getTrainerGroups = async (trainerId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${GROUP_ENDPOINT}/trainers/${trainerId}`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        console.log(data);
        return data;
    })
    .catch(error => {
        console.error('Error:', error);
        throw error;
    });
}

export const getGroup = async (groupId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${GROUP_ENDPOINT}/${groupId}`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        console.log(data);
        return data;
    })
    .catch(error => {
        console.error('Error:', error);
        throw error;
    });
}

export const editGroup = async (groupId, name, description, membersId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${GROUP_ENDPOINT}/${groupId}`,{
        method: 'PUT',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, description: description, membersId: membersId})
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        console.log(data);
        return data;
    })
    .catch(error => {
        console.error('Error:', error);
        throw error;
    });
}

export const deleteGroup = async (groupId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${GROUP_ENDPOINT}/${groupId}`,{
        method: 'DELETE',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        return response.json();
    })
    .then(data => {
        console.log(data);
        return data;
    })
    .catch(error => {
        console.error('Error:', error);
        throw error;
    });
}