import { useState } from 'react';
import { Button } from 'antd';
import { ArrowLeftOutlined } from '@ant-design/icons';

export const withSubmit = Component => props => {
    const [values, setValues] = useState(null);

    const onChange = values => setValues(values);

    const onSubmit = event => {
        event.preventDefault();
        console.log('dasd', values);
    };

    return (
        <div>
            <ArrowLeftOutlined onClick={() => props.goBack()} />
            <form onSubmit={onSubmit}>
                <Component {...props} onChange={onChange} />
                <Button htmlType='submit'>Зберегти</Button>
            </form>
        </div>
    );
};