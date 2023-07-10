package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>(); // static 사용
    private static long sequence = 0L; //static 사용

    public Member save(Member member) {
        log.info("member" + member);
        member.setId(++sequence);

        log.info("save : member={}", member);
        store.put(member.getId(),member);
        return member;
    }

    public Member findById (long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        List<Member> all = findAll();
        // 반환타입이 Member 일때
      /*  for(Member m : all) {
            if (m.getLoginId().equals(loginId)) {
                return m;
            }
        }
        return null;*/

        // 위와 같은데 Optional 일때
       /* for(Member m : all) {
            if (m.getLoginId().equals(loginId)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();*/

        // 위의 for 문과 같은데 람다로 표현
        // list를 stream으로 바꿈
        // filter 에 만족하는 것만 다음 단계로 이동
        // findFirst 처음에 나온 애만 리턴됨!
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 초기화용
    public void clearStore() {
        store.clear();
    }


}
