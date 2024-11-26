import React from 'react';
import './Paginator.css'; // Opcional, para estilos específicos

const Paginator = ({ currentPage, totalPages, onPageChange }) => {
    if (totalPages <= 1) return null; // No mostrar la paginación si hay una sola página

    return (
        <div className="paginator">
            <button
                onClick={() => onPageChange(currentPage - 1)}
                disabled={currentPage === 1}
                className="paginator-button"
            >
                Anterior
            </button>

            {Array.from({ length: totalPages }, (_, index) => (
                <button
                    key={index}
                    onClick={() => onPageChange(index + 1)}
                    className={`paginator-button ${currentPage === index + 1 ? 'active' : ''}`}
                >
                    {index + 1}
                </button>
            ))}

            <button
                onClick={() => onPageChange(currentPage + 1)}
                disabled={currentPage === totalPages}
                className="paginator-button"
            >
                Siguiente
            </button>
        </div>
    );
};

export default Paginator;
