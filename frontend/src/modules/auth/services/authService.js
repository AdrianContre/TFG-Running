export const loginService = async (username, password) => {
    return fetch("http://localhost:8081/api/v1/auth/login", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username: username, password: password })
    })
    .then(response => {
      return response.json();
    })
    .then(data => {
      return data;
    })
    .catch(error => {
      console.error('Error:', error);
      throw error;
    });
  };

export const registerService = async (name, surname, mail, username, password, isTrainer) => {
  return fetch ("http://localhost:8081/api/v1/auth/register", {
    method: 'POST', 
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      name: name, 
      surname: surname,
      mail: mail,
      username: username,
      password: password,
      weight: 0, 
      height: 0,
      fcMax: 0,
      trainer: isTrainer,
      experience: 0
    })
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
  