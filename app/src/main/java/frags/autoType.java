package frags;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class autoType implements NativeKeyListener {
    static Robot robot;
    static volatile boolean stopTyping = false;
    static volatile boolean isTyping = false;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new autoType());
            System.out.println("Robot active! Press [END] to type, [ESC] to quit.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void typeString(String str) {
        stopTyping = false;

        for (int i = 1; i > 0; i--) {
            if (stopTyping) return;
            System.out.println("Starting in " + i + "...");
            robot.delay(1000);
        }

        String[] lines = str.split("\n", -1);

        for (int i = 0; i < lines.length; i++) {
            if (stopTyping) return;

            String line = lines[i];

            // Count leading whitespace on this line
            int leadingWS = 0;
            for (char c : line.toCharArray()) {
                if (c == ' ' || c == '\t') leadingWS++;
                else break;
            }


            int startAt = (i == 0) ? 0 : leadingWS;

            for (int ci = startAt; ci < line.length(); ci++) {
                if (stopTyping) return;
                typeCharacter(line.charAt(ci));
                robot.delay(1);
            }

            if (i < lines.length - 1) {


                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                robot.delay(10); 

                robot.keyPress(KeyEvent.VK_HOME);
                robot.keyRelease(KeyEvent.VK_HOME);
                robot.delay(10);


                String nextLine = lines[i + 1];
                for (char c : nextLine.toCharArray()) {
                    if (stopTyping) return;
                    if (c == ' ' || c == '\t') {
                        typeCharacter(c);
                        robot.delay(1);
                    } else {
                        break;
                    }
                }
            }
        }

    }

    private static void typeCharacter(char c) {
        boolean shift = false;
        int keyCode;

        if (Character.isUpperCase(c)) {
            shift = true;
            keyCode = c;
        } else if (Character.isLowerCase(c)) {
            keyCode = Character.toUpperCase(c);
        } else {
            switch (c) {
                case '!': shift = true; keyCode = KeyEvent.VK_1; break;
                case '@': shift = true; keyCode = KeyEvent.VK_2; break;
                case '#': shift = true; keyCode = KeyEvent.VK_3; break;
                case '$': shift = true; keyCode = KeyEvent.VK_4; break;
                case '%': shift = true; keyCode = KeyEvent.VK_5; break;
                case '^': shift = true; keyCode = KeyEvent.VK_6; break;
                case '&': shift = true; keyCode = KeyEvent.VK_7; break;
                case '*': shift = true; keyCode = KeyEvent.VK_8; break;
                case '(': shift = true; keyCode = KeyEvent.VK_9; break;
                case ')': shift = true; keyCode = KeyEvent.VK_0; break;
                case '_': shift = true; keyCode = KeyEvent.VK_MINUS; break;
                case '+': shift = true; keyCode = KeyEvent.VK_EQUALS; break;
                case '{': shift = true; keyCode = KeyEvent.VK_OPEN_BRACKET; break;
                case '}': shift = true; keyCode = KeyEvent.VK_CLOSE_BRACKET; break;
                case '|': shift = true; keyCode = KeyEvent.VK_BACK_SLASH; break;
                case ':': shift = true; keyCode = KeyEvent.VK_SEMICOLON; break;
                case '"': shift = true; keyCode = KeyEvent.VK_QUOTE; break;
                case '<': shift = true; keyCode = KeyEvent.VK_COMMA; break;
                case '>': shift = true; keyCode = KeyEvent.VK_PERIOD; break;
                case '?': shift = true; keyCode = KeyEvent.VK_SLASH; break;
                case '~': shift = true; keyCode = KeyEvent.VK_BACK_QUOTE; break;
                case '\n': keyCode = KeyEvent.VK_ENTER; break;
                case '\t': keyCode = KeyEvent.VK_TAB; break;
                case ' ':  keyCode = KeyEvent.VK_SPACE; break;
                case '=':  keyCode = KeyEvent.VK_EQUALS; break;
                case '\'': keyCode = KeyEvent.VK_QUOTE; break;
                case '.':  keyCode = KeyEvent.VK_PERIOD; break;
                case ',':  keyCode = KeyEvent.VK_COMMA; break;
                case '-':  keyCode = KeyEvent.VK_MINUS; break;
                case '/':  keyCode = KeyEvent.VK_SLASH; break;
                case '\\': keyCode = KeyEvent.VK_BACK_SLASH; break;
                case '[':  keyCode = KeyEvent.VK_OPEN_BRACKET; break;
                case ']':  keyCode = KeyEvent.VK_CLOSE_BRACKET; break;
                case ';':  keyCode = KeyEvent.VK_SEMICOLON; break;
                case '`':  keyCode = KeyEvent.VK_BACK_QUOTE; break;
                case '0':  keyCode = KeyEvent.VK_0; break;
                case '1':  keyCode = KeyEvent.VK_1; break;
                case '2':  keyCode = KeyEvent.VK_2; break;
                case '3':  keyCode = KeyEvent.VK_3; break;
                case '4':  keyCode = KeyEvent.VK_4; break;
                case '5':  keyCode = KeyEvent.VK_5; break;
                case '6':  keyCode = KeyEvent.VK_6; break;
                case '7':  keyCode = KeyEvent.VK_7; break;
                case '8':  keyCode = KeyEvent.VK_8; break;
                case '9':  keyCode = KeyEvent.VK_9; break;
                default:   keyCode = Character.toUpperCase(c); break;
            }
        }

        if (shift) robot.keyPress(KeyEvent.VK_SHIFT);
        try {
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        } catch (Exception e) { /* skip unmappable chars */ }
        if (shift) robot.keyRelease(KeyEvent.VK_SHIFT);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_END) {
            if (!isTyping) {
                isTyping = true;

                // Triple-quoted text block: this must be a straight JS/Java string literal, no Java escaping.
                String codeToType = """
import random
import re

# ═══════════════════════════════════════════════════════════
#  BACKGROUND & STARS
# ═══════════════════════════════════════════════════════════
app.background = 'black'
for i in range(30):
    Star(randrange(0, 400), randrange(0, 400), 2, 5, fill='white')
shooting_stars = []

# ═══════════════════════════════════════════════════════════
#  APP STATE
# ═══════════════════════════════════════════════════════════
app.steps     = 0
app.hue2      = 0
app.r1        = True
app.typing    = False
app.input     = ""
app.caret     = 0
app.caretVis  = True
app.caretTick = 0

# ═══════════════════════════════════════════════════════════
#  STATIC UI
# ═══════════════════════════════════════════════════════════
Rect(0, 0, 125, 400, fill='darkGray')
Label("History", 62, 15, size=16, fill='black')
Line(5, 30, 120, 30, fill='black', opacity=80)
historyGroup = Group()

coreGlow = Circle(200, 100, 35, fill='cyan', opacity=15)
core = Circle(200, 100, 25,
              fill=gradient(rgb(0, 0, 255), 'blue', start='center'),
              border='white', borderWidth=2)
Label('CS1 AI', 200, 135, size=20, fill='white')

responseBox   = Rect(50, 145, 325, 125, fill='white')
responseGroup = Group()
graphGroup    = Group()

Rect(0, 200, 400, 200, fill='gray', opacity=20)
promptBox   = Rect(90, 300, 250, 75, fill='white')
promptGroup = Group()

helpBtn = Circle(365, 365, 15, fill='white')
Label('?', 365, 365, size=20, fill='black')

# ═══════════════════════════════════════════════════════════
#  AI ENGINE
# ═══════════════════════════════════════════════════════════

_CORPUS = (
    "intelligence emerges from patterns hidden in data and structure "
    "algorithms find order within apparent chaos using logic and rules "
    "mathematics underlies all computing science and problem solving "
    "variables hold values that change as programs run and evolve "
    "functions transform inputs into predictable and useful outputs "
    "loops repeat actions until a condition becomes false or a goal is met "
    "graphs reveal relationships and trends across collections of numbers "
    "statistics summarize raw numbers into meaningful insights and patterns "
    "the mean divides the total sum by the count of all values present "
    "median is the middle value when numbers are sorted in ascending order "
    "sorting arranges data from smallest to largest and reveals structure "
    "problems become manageable when broken into smaller well defined steps "
    "patterns repeat across scales connecting simple rules to complex results "
    "abstraction hides unnecessary detail and exposes the essential structure "
    "recursion solves big problems by reducing them to smaller similar ones "
    "memory stores context that shapes every future decision and response "
    "creativity connects existing ideas in new surprising and useful ways "
    "questions reveal hidden assumptions and open paths to deeper understanding "
    "answers build from evidence and careful logical reasoning step by step "
    "learning updates beliefs whenever new information conflicts with old models "
    "data tells stories when analyzed carefully with the right perspective "
    "thinking requires structure logic and the ability to form connections "
    "systems grow complex as simple interacting components follow basic rules "
    "curiosity drives discovery by pushing beyond familiar and comfortable answers "
    "analysis breaks problems into parts then rebuilds them with new understanding "
)

brain = {
    'hist_disp' : [],
    'working_m' : [],
    'mood'      : 'neutral',
    'markov'    : {},
    'ctx'       : {'nums': [], 'result': None, 'topic': None, 'turn': 0},
}

# ── Markov chain ──────────────────────────────────────────
def _buildM(text):
    ws = text.lower().split()
    c = {}
    for a, b in zip(ws, ws[1:]):
        if a not in c:
            c[a] = []
        c[a].append(b)
    return c

brain['markov'] = _buildM(_CORPUS)

def _learn(text):
    n = _buildM(text.lower())
    for w in n:
        if w not in brain['markov']:
            brain['markov'][w] = []
        brain['markov'][w] += n[w]

def _mkv(seeds, length=12):
    c = brain['markov']
    good = [w for w in seeds if w in c]
    word = random.choice(good) if good else random.choice(list(c.keys()))
    out = [word]
    for _ in range(length):
        if word not in c:
            break
        word = random.choice(c[word])
        out.append(word)
    return ' '.join(out).capitalize() + '.'

# ── Mood ──────────────────────────────────────────────────
_POS = {'good','great','happy','awesome','love','cool','fun','nice',
        'excited','wonderful','amazing','fantastic','interesting'}
_NEG = {'bad','sad','angry','hate','tired','awful','boring','terrible',
        'ugh','frustrated','wrong','broken','confused','stuck'}

def _moodOf(text):
    ws = set(text.lower().split())
    s = len(ws & _POS) - len(ws & _NEG)
    return 'positive' if s > 0 else ('negative' if s < 0 else 'neutral')

# ── Number extraction & formatting ───────────────────────
def _getNums(text):
    return [float(m) for m in re.findall(r'-?\\d+\\.?\\d*', text)]

def _fmt(v):
    if isinstance(v, float) and v == int(v):
        return str(int(v))
    return str(round(v, 3))

# ── Math solver (shunting-yard) ───────────────────────────
def _mathSolve(text):
    toks = re.findall(r'\\d+\\.?\\d*|\\+|\\-|\\*|\\/|\\^|\\(|\\)', text)
    if len(toks) < 3:
        return None
    prec = {'+': 1, '-': 1, '*': 2, '/': 2, '^': 3}
    out, ops = [], []
    for t in toks:
        if re.match(r'^\\d', t):
            out.append(float(t))
        elif t in prec:
            while ops and ops[-1] in prec and prec.get(ops[-1], 0) >= prec[t]:
                out.append(ops.pop())
            ops.append(t)
        elif t == '(':
            ops.append(t)
        elif t == ')':
            while ops and ops[-1] != '(':
                out.append(ops.pop())
            if ops:
                ops.pop()
    out += ops[::-1]
    stk = []
    for t in out:
        if isinstance(t, float):
            stk.append(t)
        else:
            if len(stk) < 2:
                return None
            b, a = stk.pop(), stk.pop()
            if t == '+':   stk.append(a + b)
            elif t == '-': stk.append(a - b)
            elif t == '*': stk.append(a * b)
            elif t == '/':
                if b == 0: return 'undefined'
                stk.append(round(a / b, 6))
            elif t == '^': stk.append(round(a ** b, 6))
    return None if not stk else _fmt(stk[0])

# ── Statistics ────────────────────────────────────────────
def _statsOf(nums):
    if not nums:
        return None
    n  = len(nums)
    su = sum(nums)
    sn = sorted(nums)
    mid = n // 2
    med = sn[mid] if n % 2 else (sn[mid - 1] + sn[mid]) / 2
    cnt = {}
    for x in nums:
        cnt[x] = cnt.get(x, 0) + 1
    mo = max(cnt, key=lambda k: cnt[k])
    return {
        'mean'  : round(su / n, 4),
        'median': med,
        'mode'  : mo,
        'range' : round(max(nums) - min(nums), 4),
        'sum'   : round(su, 4),
        'count' : n,
        'min'   : min(nums),
        'max'   : max(nums),
    }

# ── Chart drawing ─────────────────────────────────────────
# Area inside responseBox Rect(50,145,325,125) → x:50-375, y:145-270
_CX, _CY, _CW, _CH = 68, 153, 278, 102

def _drawChart(nums, kind='bar'):
    graphGroup.clear()
    if len(nums) < 2:
        return False
    hi, lo = max(nums), min(nums)
    sp = hi - lo if hi != lo else max(abs(hi), 1)
    n  = len(nums)
    x0, y0, cw, ch = _CX, _CY, _CW, _CH

    # Horizontal grid lines
    for k in range(4):
        gy = y0 + int(k * (ch - 10) / 3)
        graphGroup.add(Line(x0, gy, x0 + cw, gy, fill='lightGray', opacity=40))

    if kind == 'line':
        pts = []
        for i, v in enumerate(nums):
            px = x0 + (int(i * cw / (n - 1)) if n > 1 else cw // 2)
            py = y0 + (ch - 10) - int((v - lo) / sp * (ch - 16)) + 3
            pts.append((px, py))
        for i in range(len(pts) - 1):
            graphGroup.add(Line(pts[i][0], pts[i][1],
                                pts[i + 1][0], pts[i + 1][1],
                                fill='dodgerBlue', lineWidth=2))
        for px, py in pts:
            graphGroup.add(Circle(px, py, 3, fill='tomato',
                                  border='white', borderWidth=1))
    else:  # bar
        step = max(1, cw // n)
        bw   = max(1, step - 2)
        for i, v in enumerate(nums):
            bh   = max(2, int((v - lo) / sp * (ch - 16)))
            bx   = x0 + i * step
            by   = y0 + (ch - 10) - bh
            r2   = (v - lo) / sp if sp else 0.5
            graphGroup.add(Rect(bx, by, bw, bh,
                                fill=rgb(int(40 + 180 * r2),
                                         int(140 - 100 * r2), 210)))
            if bw > 22:
                graphGroup.add(Label(_fmt(v), bx + bw // 2, by - 6,
                                     size=8, fill='black', align='center'))

    # Axes
    graphGroup.add(Line(x0, y0, x0, y0 + ch - 10, fill='black', lineWidth=1))
    graphGroup.add(Line(x0, y0 + ch - 10, x0 + cw, y0 + ch - 10,
                        fill='black', lineWidth=1))
    # Chart-type label (top)
    graphGroup.add(Label(kind.upper() + ' CHART', x0 + cw // 2, y0 - 4,
                         size=9, fill='dimGray', align='center'))
    # Stats summary (bottom of box)
    st = _statsOf(nums)
    info = ('n=' + str(st['count']) +
            '  min=' + _fmt(st['min']) +
            '  max=' + _fmt(st['max']) +
            '  avg=' + str(st['mean']))
    graphGroup.add(Label(info, x0 + cw // 2, y0 + ch + 3,
                         size=8, fill='dimGray', align='center'))
    return True

# ── Response assembly ─────────────────────────────────────
_OPN = [
    "Thinking through this,", "Working that out,", "Looking at this,",
    "Based on your input,", "Analyzing that,", "Here's my take:",
    "Considering the context,", "Interesting —", "Processing this,",
    "Let me work through it:", "From what I gather,",
]
_CLS = [
    "Does that help?", "Want to explore more?", "Any follow-up questions?",
    "Let me know if you need more.", "Feel free to ask anything else.",
    "What else is on your mind?", "I can go deeper if you'd like.",
]

def _wrap(core_text):
    parts = [random.choice(_OPN), core_text]
    if random.random() > 0.5:
        parts.append(random.choice(_CLS))
    return ' '.join(parts)

# ── Intent detection ──────────────────────────────────────
def _getIntent(t):
    if re.search(r'\\b(graph|plot|chart|draw|visuali[sz]e)\\b', t):
        return 'graph'
    if re.search(r'\\b(mean|average|median|mode|range|sort|stat|variance|sum|total)\b', t):
        return 'stats'
    if re.search(r'\\d', t) and re.search(r'[+\\-*/^]', t):
        return 'math'
    if len(re.findall(r'-?\\d+\\.?\\d*', t)) >= 3:
        return 'stats'
    if re.match(r'^(hi|hello|hey|howdy|sup|yo)\\b', t):
        return 'greet'
    if re.search(r'\\b(bye|goodbye|cya|see you|later|take care)\\b', t):
        return 'farewell'
    if re.search(r'\\b(who are you|what are you|your name|who made)\\b', t):
        return 'identity'
    if re.search(r'\\b(what can you|can you|what do you do|your abil)\\b', t):
        return 'capability'
    if '?' in t:
        return 'question'
    return 'general'

# ── Main response generator ───────────────────────────────

def generateResponse(raw):
    text = raw.lower().strip()
    brain['mood'] = _moodOf(text)
    brain['working_m'].append(text)
    if len(brain['working_m']) > 8:
        brain['working_m'].pop(0)
    _learn(text)
    brain['ctx']['turn'] += 1
    ctx = brain['ctx']

    nums   = _getNums(text)
    intent = _getIntent(text)

    # Context: substitute "that"/"it" with last computed result
    if ctx['result'] and re.search(r'\\b(that|it|the result|the answer)\\b', text):
        subbed = re.sub(r'\\bthat\\b|\\bit\\b', ctx['result'], text)
        if re.search(r'[+\\-*/^]', subbed):
            text   = subbed
            nums   = _getNums(text)
            intent = 'math'

    if nums:
        ctx['nums'] = nums

    graphGroup.clear()  # always wipe old chart first

    # ── Graph ──────────────────────────────────────────────
    if intent == 'graph':
        data = nums if len(nums) >= 2 else ctx['nums']
        if len(data) >= 2:
            kind = 'line' if re.search(r'\\bline\\b', raw.lower()) else 'bar'
            if _drawChart(data, kind):
                return ""   # chart self-labels; no text overlay needed
        return _wrap("I need at least 2 numbers to chart. Try: graph 5, 10, 3, 8, 6")

    # ── Stats ───────────────────────────────────────────────
    if intent == 'stats':
        data = nums if nums else ctx['nums']
        if data:
            st = _statsOf(data)
            if re.search(r'\\bmean\\b|\\baverage\\b', text):
                return _wrap("The mean is " + str(st['mean']) + ".")
            if re.search(r'\\bmedian\\b', text):
                return _wrap("The median is " + _fmt(st['median']) + ".")
            if re.search(r'\\bmode\\b', text):
                return _wrap("The mode is " + _fmt(st['mode']) + ".")
            if re.search(r'\\bsort\\b', text):
                return _wrap("Sorted: " + ', '.join(_fmt(x) for x in sorted(data)) + ".")
            if re.search(r'\\bmax\\b', text):
                return _wrap("The max is " + _fmt(st['max']) + ".")
            if re.search(r'\\bmin\\b', text):
                return _wrap("The min is " + _fmt(st['min']) + ".")
            if re.search(r'\\b(sum|total)\\b', text):
                return _wrap("The sum is " + str(st['sum']) + ".")
            return ("n=" + str(st['count']) +
                    "  mean=" + str(st['mean']) +
                    "  median=" + _fmt(st['median']) +
                    "  range=" + str(st['range']) + ".")
        return _wrap("Share some numbers and I'll crunch the stats.")

    # ── Math ────────────────────────────────────────────────
    if intent == 'math':
        result = _mathSolve(text)
        if result:
            ctx['result'] = result
            return _wrap("That works out to " + result + ".")
        return _wrap("I see an expression but couldn't solve it. Use: + - * / ^ ()")

    # ── Greeting ────────────────────────────────────────────
    if intent == 'greet':
        pfx = random.choice(["Hello!", "Hey there!", "Hi!", "Greetings!"])
        return pfx + " " + _mkv(['intelligence', 'patterns', 'thinking', 'logic'], 9)

    # ── Farewell ────────────────────────────────────────────
    if intent == 'farewell':
        pfx = random.choice(["Goodbye!", "See you!", "Later!", "Take care!"])
        return pfx + " It was great thinking together."

    # ── Identity ────────────────────────────────────────────
    if intent == 'identity':
        return ("I'm CS1 AI — running Markov chains, intent detection, "
                "and a self-growing knowledge base. I learn from every message.")

    # ── Capability ──────────────────────────────────────────
    if intent == 'capability':
        return ("I solve math (2^10+5), compute stats (mean, median, sort), "
                "draw charts (graph 4,8,15,16,23), and chat. "
                "I also learn from everything you type!")

    # ── Question ────────────────────────────────────────────
    if intent == 'question':
        seeds = [w for w in text.replace('?', '').split() if len(w) > 3]
        return _wrap(_mkv(seeds, random.randint(10, 16)))

    # ── General fallback ────────────────────────────────────
    seeds = [w for w in text.split() if len(w) > 3]
    m = brain['mood']
    if m == 'positive':
        pfx = random.choice(["That's great!", "Awesome.", "Love that.", "Nice thinking."])
    elif m == 'negative':
        pfx = random.choice(["I understand.", "That sounds tough.", "Let's work through it."])
    else:
        pfx = random.choice(_OPN)
    fallback = seeds if seeds else ['patterns', 'thinking', 'intelligence']
    return pfx + " " + _mkv(fallback, random.randint(10, 16))

# ═══════════════════════════════════════════════════════════
#  RENDERING HELPERS
# ═══════════════════════════════════════════════════════════
_meas = Label("", 0, 0, size=13, visible=False)

def updateHistory(query):
    short = query[:14] + ("..." if len(query) > 14 else "")
    brain['hist_disp'].insert(0, short)
    brain['hist_disp'] = brain['hist_disp'][:8]
    historyGroup.clear()
    for i, item in enumerate(brain['hist_disp']):
        historyGroup.add(Label(item, 10, 45 + i * 38,
                               size=10, fill='white', align='left'))

def wrapResponseText(text):
    responseGroup.clear()
    if not text.strip():
        return  # graph mode — chart lives in graphGroup, no text overlay
    lines, cur = [], ""
    for word in text.split():
        test = (cur + " " + word).strip()
        _meas.value = test
        if _meas.width <= 290:
            cur = test
        else:
            lines.append(cur)
            cur = word
    if cur:
        lines.append(cur)
    for i, line in enumerate(lines[:6]):
        responseGroup.add(Label(line, 213, 158 + i * 19,
                                size=13, fill='black', align='center'))

def wrapPromptText(text):
    promptGroup.clear()
    if not text and not app.typing:
        promptGroup.add(Label("Ask something >_<", 140, 325,
                              size=12, fill='black', opacity=50, align='left'))
        return
    display = text
    if app.typing:
        c = "|" if app.caretVis else " "
        display = display[:app.caret] + c + display[app.caret:]
    charPL = 26
    lines = [display[i:i + charPL]
             for i in range(0, max(len(display), 1), charPL)]
    shown = lines[-3:]
    for i, ln in enumerate(shown):
        promptGroup.add(Label(ln, 140, 310 + i * 18,
                              size=12, fill='black', align='left'))

wrapResponseText("Hi! Ask me anything — math, stats, charts, or just chat.")
wrapPromptText("")

# ═══════════════════════════════════════════════════════════
#  HELP MENU
# ═══════════════════════════════════════════════════════════
menu = Group(
    Rect(0, 0, 400, 400, fill='black', opacity=60),
    Rect(55, 50, 290, 300, fill='white'),
    Label('CS1 AI  Help',                          200,  85, size=18, fill='black'),
    Label('Click the input box to start typing.',  200, 118, size=12, fill='black'),
    Label('Press ENTER to send your message.',     200, 140, size=12, fill='black'),
    Label('Math:  12 * 4 + 3   or   2 ^ 10',      200, 168, size=12, fill='black'),
    Label('Stats: mean of 4, 8, 15, 23, 42',       200, 190, size=12, fill='black'),
    Label('       or just list numbers: 3,7,2,9',  200, 208, size=11, fill='gray'),
    Label('Chart: graph 5, 10, 3, 8, 6',           200, 230, size=12, fill='black'),
    Label('Line:  line chart 2, 4, 6, 4, 2',       200, 250, size=12, fill='black'),
    Label('Ref last result: add 5 to that',        200, 270, size=12, fill='black'),
    Label('Hold B for shooting stars!',            200, 293, size=12, fill='black'),
    Label('Click anywhere to close.',              200, 330, size=11, fill='gray'),
)
menu.visible = False

# ═══════════════════════════════════════════════════════════
#  EVENT HANDLERS
# ═══════════════════════════════════════════════════════════
def _newStar():
    c = rgb(randrange(200, 255), randrange(200, 255), randrange(0, 80))
    s = Star(200, 100, 3, 5, fill=c)
    s.dx = randrange(-6, 7)
    s.dy = randrange(-6, 7)
    if s.dx == 0 and s.dy == 0:
        s.dx = 3
    shooting_stars.append(s)

def onStep():
    app.steps += 1
    if app.r1:
        app.hue2 += 4
        if app.hue2 >= 255: app.r1 = False
    else:
        app.hue2 -= 4
        if app.hue2 <= 0:   app.r1 = True
    core.fill = gradient(rgb(0, app.hue2, 255), 'blue', start='center')
    for s in shooting_stars:
        s.centerX += s.dx
        s.centerY += s.dy
        if s.centerX < 0:   s.centerX = 400
        if s.centerX > 400: s.centerX = 0
        if s.centerY < 0:   s.centerY = 400
        if s.centerY > 400: s.centerY = 0
    app.caretTick += 1
    if app.caretTick % 20 == 0:
        app.caretVis = not app.caretVis
        if app.typing:
            wrapPromptText(app.input)

def onKeyHold(keys):
    if 'b' in keys and app.steps % 5 == 0:
        _newStar()

def onMousePress(x, y):
    if menu.visible:
        menu.visible = False
        return
    if helpBtn.contains(x, y):
        menu.visible = True
        return
    if promptBox.contains(x, y):
        app.typing = True
        app.caret  = len(app.input)
        wrapPromptText(app.input)
    else:
        if app.typing:
            app.typing = False
            wrapPromptText(app.input)

def onKeyPress(key):
    if not app.typing:
        return
    if key == 'enter':
        if app.input.strip():
            resp = generateResponse(app.input)
            wrapResponseText(resp)
            updateHistory(app.input)
            app.input  = ""
            app.caret  = 0
            app.typing = False
            wrapPromptText("")
        return
    if key == 'backspace':
        if app.caret > 0:
            app.input = app.input[:app.caret - 1] + app.input[app.caret:]
            app.caret -= 1
            wrapPromptText(app.input)
        return
    if key == 'left':
        if app.caret > 0:
            app.caret -= 1
            wrapPromptText(app.input)
        return
    if key == 'right':
        if app.caret < len(app.input):
            app.caret += 1
            wrapPromptText(app.input)
        return
    if key == 'space':
        app.input = app.input[:app.caret] + ' ' + app.input[app.caret:]
        app.caret += 1
        wrapPromptText(app.input)
        return
    if len(key) == 1:
        app.input = app.input[:app.caret] + key + app.input[app.caret:]
        app.caret += 1
        wrapPromptText(app.input)
""";

                new Thread(() -> {
                    typeString(codeToType);
                    isTyping = false;
                }).start();
            }
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.out.println("EMERGENCY STOP!");
            stopTyping = true;
            isTyping = false;
            System.exit(0);
        }
    }

    @Override public void nativeKeyReleased(NativeKeyEvent e) {}
    @Override public void nativeKeyTyped(NativeKeyEvent e) {}
}