#1������ʽ��̼��
    0����̷�ʽ
        ����ʽ���
            ��������̣�Java��
        �߼�ʽ���
        ����ʽ���
    1������ʽ��̵�����  
        ��������Ϊ�������ݸ�����һ������
        һ������������Ϊ����һ�������ķ���ֵ
    2���޸�����
        �����ĸ�����ָ���Ǻ����ڵ��ù����У��������˷���ֵ֮�⣬���޸��˺����ⲿ��״̬��
        ����ʽ�����Ϊ�������ĸ�����Ӧ�ñ���������
        
        ���Ѽ�����̵����ǡ���ѧ����������ֵ���������״̬�Ϳɱ����ݡ�
    3������ʽ�ģ� Declarative)
        ��Ҫ����ֻ��������Ҫ������������⼴�ɡ�
    4������Ķ���
        ���޸Ķ���
    5�����ڲ���
#2������ʽ��̻���
    1��Functionallnterface ����ʽ�ӿ�
        ����ӿ�����ֻ����һ�����󷽷�
            �����徲̬����
            ������Ĭ�Ϸ���
            ����java.lang.Object�е�public����
        ���õĺ���ʽ�ӿ��У�
            Cosumer �� Supplier ��Ӧ��һ���������ߣ�һ�����ṩ�ߡ�
                forEach----Cosumer
            Predicate �����ж϶����Ƿ����ĳ���������������������˶���
                filter
            Function �ǽ�һ������ת��Ϊ��һ�����󣬱���˵Ҫװ����߲���ĳ������
                map
            UnaryOperator ���պͷ���ͬ���Ͷ���һ�����ڶԶ����޸����ԡ�
            BinaryOperator ��������Ϊ�ϲ�����
    2��lambda ���ʽ
        lambda ���ʽ����������������һ��û�к������ĺ����壬��������Ϊ����ֱ�Ӵ��ݸ���صĵ����ߡ����������ǿ��Java
        ���Եı��������
        
        һ�ֽ��յġ�������Ϊ�ķ�ʽ
    3����������
        ���������� Java 8 ������������� lambda ���ʽ��һ���ֶΡ���ͨ�������ͷ���������λһ����̬��������ʵ��������
        
        ���������� Java 8 �е�ʹ�÷ǳ����ܵ���˵�����Է�Ϊ���¼��֡�
        �� ��̬�������� �� ClassName::methodName 
        �� ʵ���ϵ� ʵ���������ã� instanceReference:: methodName ��
        �������ϵ�ʵ���������� �� super::methodName ��
        �� �����ϵ�ʵ���������� �� ClassName::methodName ��
        �����췽�����ã� Class::new ��
        �� ���鹹�췽������ �� TypeName[]::new ��
#3�����ϴ���
    1��Stream ���
        ���м̳��� Collection �Ľӿڶ�����ת��Ϊ Stream��
    2��Stream �ķ�����Ϊ���ࡣ
        һ��ж�����ֵ��
            ����ֵ�� Stream
        һ��м�����ֵ��
#4��һ��һ�����뺯��ʽ���
    Java 8 �ж� lambda ���ʽ�Ĵ������� ͬ ���������ʵ�� �� ������д����
    �ͱ�̷�ʽ���������Ե�����
```java
public class Test{
    @Test
    public void test3(){
        int[] arr= { 1, 3, 4, 5, 6, 7, 8, 9, 10 };
        // Arrays.stream(arr) ����һ��IntStream ������
        IntStream intStream = Arrays.stream(arr);
        intStream.forEach(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });

        // IntStream �ӿ����Ʊ�ʡ����
        Arrays.stream(arr).forEach((int value)-> System.out.println(value));
        //��������Ҳ�ǿ����Ƶ�����
        Arrays.stream(arr).forEach((value)-> System.out.println(value));
        //��������
        Arrays.stream(arr).forEach( System.out::println);
    }
}
```
#5���������벢������
    1��ʹ�ò�������������
        IntStream.parallel()
        list.parallelStream()
        
        Arrays.parallelSort(arr);
        Arrays.parallelSetAll(arr, (i)->r.nextInt());
#6����ǿ�� Future: CompletableFuture
    
