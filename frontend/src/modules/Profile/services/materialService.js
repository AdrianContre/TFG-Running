const END_POINT_MATERIALS = "http://localhost:8081/api/v1/materials"


export const getUserMaterials = async (id) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_MATERIALS}/runners/${id}`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        }
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        return data.data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; 
      });
}


export const deleteMaterial = async (materialId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_MATERIALS}/${materialId}`,{
      method: 'DELETE',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      }
  })
  .then(response => {
      return response.json();
    })
    .then(data => {
      console.log(data);
      return data.data
    })
    .catch(error => {
      console.error('Error:', error);
      throw error;
    });
}

export const createMaterial = async (brand, model, description, wear, runnerId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(END_POINT_MATERIALS,{
      method: 'POST',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      }, 
      body: JSON.stringify({brand: brand, model: model, description: description, wear: wear, runnerId: runnerId})
  })
  .then(response => {
      return response.json();
    })
    .then(data => {
      return data.data
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

export const editMaterial = async (brand, model, description, wear, materialId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_MATERIALS}/${materialId}`,{
      method: 'PUT',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      }, 
      body: JSON.stringify({brand: brand, model: model, description: description, wear: wear})
  })
  .then(response => {
      return response.json();
    })
    .then(data => {
      return data.data
    })
    .catch(error => {
      console.error('Error:', error);
      throw error; 
    });
}

export const uploadPhoto = async (materialId, picture) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_MATERIALS}/${materialId}/photo`,{
      method: 'PUT',
      headers: {
      'Authorization': authHeader,
      }, 
      body: picture
  })
  .then(response => {
      return response.json();
    })
    .then(data => {
      return data.data
    })
    .catch(error => {
      console.error('Error:', error);
      throw error; 
    });
}