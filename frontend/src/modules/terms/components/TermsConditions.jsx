import { useNavigate } from 'react-router'
import '../styles/terms.css'
function TermsConditions() {
    const navigate = useNavigate()
    const navigateToPrivacy = (event) => {
        event.preventDefault()
        navigate('/privacy')
    }
    return (
        <>
            <div className="terms-main-container">
                <h1>Términos y condiciones de RUNNING2ALL:</h1>
                <h6>Fecha de entrada en vigor: 12 de Diciembre de 2024</h6>
                <hr></hr>
                <div className='list-container'>
                    <section>
                        <p>¡Bienvenido/a a <strong>RUNNING2ALL</strong>!. Queremos que conozca y comprenda sus derechos y nuestras responsabilidades en relación con el uso de nuestra plataforma. Al registrarse o utilizar nuestros servicios, usted acepta los siguientes términos y condiciones. Por favor, léalos detenidamente.</p>
                    </section>
                    <ol>
                        <h3><li>Privacidad y protección de datos</li></h3>
                        <ul>
                            <li>Respetamos su intimidad. Puede ver como recopilamos, usamos, compartimos y protegemos su información personal en nuestra <a className="custom-label-register" style={{fontSize: '16px', marginTop: '10px', cursor: 'pointer'}} onClick={navigateToPrivacy}>Política de privacidad</a>.</li>
                            <li>Los datos proporcionados deben ser exactos y mantenerse actualizados según el Artículo 4 del GDPR. Usted tiene derecho a acceder, modificar o eliminar su información personal en cualquier momento.</li>
                            
                        </ul>

                        <h3><li>Edad mínima y consentimiento legal</li></h3>
                        <ul>
                            <li>Para registrarse en <strong>RUNNING2ALL</strong>, debe de ser mayor de edad según las leyes de su país.</li>
                            <li>Si es menor de edad, se requiere el consentimiento de su representante legal.</li>
                        </ul>

                        <h3><li>Confidencialidad de la información</li></h3>
                        <ul>
                            <li>La información proporcionada será protegida de acuerdo con el artículo 5 del GDPR, garantizando su seguridad frente a accesos no autorizados, pérdidas o daños accidentales.</li>
                        </ul>

                        <h3><li>Consentimiento para el tratamiento de datos</li></h3>
                        <ul>
                            <li>Al registrarse, usted consiente expresamente que sus datos sean tratados para los fines descritos en nuestra <a className="custom-label-register" style={{fontSize: '16px', marginTop: '10px', cursor: 'pointer'}} onClick={navigateToPrivacy}>Política de privacidad</a>, conforme al artículo 6 del GDPR.</li>
                            <li>El consentimiento puede ser revocado en cualquier momento mediante la eliminación de su cuenta.</li>
                        </ul>

                        <h3><li>Eliminación de la cuenta</li></h3>
                        <ul>
                            <li>Puede eliminar su cuenta y todos sus datos personales asociados en cualquier momento desde la configuración de su perfil.</li>
                        </ul>

                        <h3><li>Modificaciones de los Términos y Condiciones</li></h3>
                        <ul>
                            <li>Nos reservamos el derecho de actualizar estos términos en cualquier momento. Le notificaremos de cualquier cambio importante antes de que entre en vigor.</li>
                        </ul>
                    </ol>
                </div>
            </div>
        </>
    )
}

export default TermsConditions;