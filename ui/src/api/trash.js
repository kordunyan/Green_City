import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYzNTA4MzQwOX0.lh0nNpRy27Pi5KlPJyo6KkwCl6Fuc_8MoGBClGNkaNPruuL1t8sIh7oMXk3p2io0etxGoyYyc9xH_S4yACk85g'
  }
});

export const exportTrash = form => {
  const formData = new FormData();
  Object.keys(form).forEach(key => {
    const value = form[key];
    if (Array.isArray(value)) {
      value.forEach(item => {
        formData.append(`${key}[]`, item, item.name);
      });
    }
    else {
      formData.append(key, value);
    }
  });
  return api.post('/export-trash', formData, { headers: { 'Content-Type': 'multipart/form-data' }});
};

export const getCompanyInfo = id => api.get(`/osbbs/${id}`);

export const getCompanyHistory = id => api.get(`/trash-exportation-by-osbb/${id}`);

export const getReport = () => api.get('/export-by-osbb');
