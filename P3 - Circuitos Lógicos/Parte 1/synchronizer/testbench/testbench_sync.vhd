library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_arith.ALL;
use IEEE.STD_LOGIC_unsigned.ALL;
use STD.TEXTIO.ALL ;

entity testbench_sync is -- no inputs or outputs
end;
architecture sim of testbench_sync is
	component sync
		port(clk: in STD_LOGIC;
		     d: in STD_LOGIC_VECTOR (3 downto 0);
		     q: out STD_LOGIC_VECTOR (3 downto 0));
	end component;
	
	signal clk_aux: STD_LOGIC;
	signal clk: STD_LOGIC;
	signal d, q: STD_LOGIC_VECTOR(3 downto 0);
	signal qexpected: STD_LOGIC_VECTOR(3 downto 0);
	constant MEMSIZE: integer := 6;
	type tvarray is array (MEMSIZE downto 0) of
	STD_LOGIC_VECTOR (8 downto 0);
	signal testvectors: tvarray;
	shared variable vectornum, errors: integer;
	
	begin
	-- instantiate device under test
	dut: sync port map (clk, d, q);
	-- generate clock
	process begin
		clk_aux <= '1'; wait for 10 ns;
		clk_aux <= '0'; wait for 5 ns;
	end process;
	-- at start of test, load vectors
	-- and pulse reset
	process is
	file tv: TEXT;
	variable i, j: integer;
	variable L: line;
	variable ch: character;
	begin
		-- read file of test vectors
		i := 0;
		FILE_OPEN (tv, "./example.tv", READ_MODE);
		while not endfile(tv) loop
			readline (tv, L);
			for j in 8 downto 0 loop
				read (L, ch);
				
				if (ch = '_') then read (L, ch);
				end if;
				
				if (ch = '0') then
					testvectors (i) (j) <= '0';
				end if;
				if (ch = '1') then 
					testvectors (i) (j) <= '1';
				end if;
				if (ch = 'u') then 
					testvectors (i) (j) <= 'U';
				end if;
			end loop;
			i := i + 1;
		end loop;
		vectornum := 0; errors := 0;
		wait;
	end process;
	-- apply test vectors on rising edge of clk
	process (clk_aux) begin
		if (clk_aux'event and clk_aux='1') then
			clk <= testvectors (vectornum) (8); --after 1 ns;
			d <= testvectors (vectornum) (7 downto 4); --after 1 ns;
			qexpected <= testvectors (vectornum)(3 downto 0); --after 1 ns;
		end if;
	end process;
	-- check results on falling edge of clk
	process (clk_aux) begin
		if (clk_aux'event and clk_aux = '0')then
			for k in 0 to 3 loop
				assert q(k)= qexpected(k)
					report "Vetor deu erro n. Teste: " &integer'image(vectornum)&
							 ". Esperado qesp ="& STD_LOGIC'image(qexpected(k))&
							 "Valor Obtido: q("&integer'image(k)&") ="& STD_LOGIC'image(q(k));
				
				if (q /= qexpected) then
					errors := errors + 1;
				end if;
			end loop;
			
			vectornum := vectornum + 1;
			--if (is_x (testvectors(vectornum))) then
			  if (vectornum = 4) then
				if (errors = 0) then
					report "Just kidding --" &
					integer'image (vectornum) &
					"tests completed successfully."
					severity failure;
				else
					report integer'image (vectornum) &
					"tests completed, errors = " &
					integer'image (errors)
					severity failure;
				end if;
			end if;
		end if;
		
	end process;
end;
