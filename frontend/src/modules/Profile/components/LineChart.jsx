import React from 'react';
import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import { format } from 'date-fns';
import { es } from 'date-fns/locale'; 


ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

const LineChart = ({ last4Weeks }) => {
    const getWeekRange = (weekIndex) => {
        const today = new Date();
        const startOfWeek = new Date(today.setDate(today.getDate() - today.getDay() + 1 - (weekIndex * 7))); 
        const endOfWeek = new Date(today.setDate(startOfWeek.getDate() + 6)); 
      
        // Usa el formato deseado para las fechas, asegurándote de que se muestren con el nombre del mes abreviado
        return `${format(startOfWeek, 'd MMM', { locale: es })} - ${format(endOfWeek, 'd MMM yyyy', { locale: es })}`;
    };


  const labels = [
    getWeekRange(3),  
    getWeekRange(2), 
    getWeekRange(1), 
    getWeekRange(0),
  ];

  const data = {
    labels: labels,
    datasets: [
      {
        label: 'Kilometraje (km)',
        data: last4Weeks,
        fill: false,
        borderColor: '#36A2EB',
        tension: 0.1,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: 'Últimas 4 Semanas:',
        color: 'black',
        font: {
            size: 16,
            weight: 'bold',
            family: 'Arial',
        },
      },
    },
  };

  return <Line data={data} options={options} />;
};

export default LineChart;
