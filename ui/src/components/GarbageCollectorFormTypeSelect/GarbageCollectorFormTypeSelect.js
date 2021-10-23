import { Card } from 'antd';

const GarbageCollectorFormTypeSelect = ({ onFormTypeSelect }) => {


    return (
      <div>
          <Card title='Забрати мусор' onClick={() => onFormTypeSelect('collect')}>

          </Card>
          <Card title='Помити контейнери' onClick={() => onFormTypeSelect('wash')}>

          </Card>
      </div>
    );
};

export default GarbageCollectorFormTypeSelect;