import {useForm} from "react-hook-form";
import styles from './styles.module.scss'
import s from '../RegisterConfirmModal/RegisterConfirmModal.module.scss';
import {Modal} from "@mui/material";
import PropTypes from "prop-types";
import {instance} from "../../../api/index.js";
import {useState} from "react";
import {useGetProfileByIdQuery, useUpdateProfileMutation} from "../../../store/services/profileService.js";


const VerifyModal = ({open, onClose,id}) => {
    const [isError,setIsError] = useState(false);
    const [errorMessage,setErrorMessage] = useState('');
    const {register, handleSubmit, formState: {isValid}} = useForm();
    const [updateProfile] = useUpdateProfileMutation(id);
    const onSubmit = async (data) => {
        try {
            await instance.post('users/verify',JSON.stringify(data));
            updateProfile({
                body: {
                    verified: true
                }, id
            });
            onClose();
        } catch (err) {
            setIsError(true)
            setErrorMessage(err.response.data)
        }

    }

    return (
        <Modal open={open} onClose={onClose} className={s.modalInner}
               disableAutoFocus>
            <form onSubmit={handleSubmit(onSubmit)} className={styles.form}>
                <h1 className={styles.title}>Верифікація профілю !</h1>
                <p className={styles.desc}>Для верифікації профілю вам потрібно ввести ПІБ зазначений в паспорті та
                    цифровий баркод з додатку Дія </p>
                <div className={styles.fields}>
                    <div className={styles.inputField}>
                        <label htmlFor="name">Прізвище</label>
                        <input type="text" {...register('lastName', {required: true})}/>
                    </div>
                    <div className={styles.inputField}>
                        <label htmlFor="name">Ім&apos;я</label>
                        <input type="text" {...register('firstName', {required: true})}/>
                    </div>
                    <div className={styles.inputField}>
                        <label htmlFor="name">По батькові</label>
                        <input type="text" {...register('middleName', {required: true})}/>
                    </div>
                    <div className={styles.inputField}>
                        <label htmlFor="name">Цифровий баркод (13 цифр)</label>
                        <input type="number" {...register('barcode', {required: true, minLength: 13, maxLength: 13})} />
                    </div>
                </div>
                {isError && <div className={styles.errorWrapper}>
                    <p className={styles.error}>Верифікація не пройдена по
                        причині: {errorMessage.length ? errorMessage : 'Сервіс не доступний'}</p>
                    <p className={styles.error}>Закрийте це вікно та спробуйте знову через декілька хвилин коли баркод
                        оновиться</p>
                </div>}
                <div className={styles.btns}>
                    <button type={"submit"} className={styles.submitBtn} disabled={!isValid || isError}>Верифікувати
                    </button>
                    <button type={"button"} className={styles.submitBtn} onClick={onClose}>Скасувати</button>
                </div>

            </form>
        </Modal>
    );
};

VerifyModal.propTypes = {
    open: PropTypes.bool.isRequired,
    onClose: PropTypes.func.isRequired,
    id: PropTypes.number
};

export default VerifyModal;
